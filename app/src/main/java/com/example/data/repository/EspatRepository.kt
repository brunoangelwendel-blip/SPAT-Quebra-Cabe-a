package com.example.data.repository

import com.example.data.local.CampaignSettingsEntity
import com.example.data.local.EspatDao
import com.example.data.local.ParticipantEntity
import com.example.data.model.EvidenceData
import com.example.data.model.Period
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID
import kotlin.random.Random

class EspatRepository(private val dao: EspatDao) {

    val allParticipants: Flow<List<ParticipantEntity>> = dao.getAllParticipants()
    val settingsFlow: Flow<CampaignSettingsEntity?> = dao.getSettings()

    fun getParticipantsByPeriod(period: Period): Flow<List<ParticipantEntity>> {
        return dao.getParticipantsByPeriod(period.name)
    }

    suspend fun getSettings(): CampaignSettingsEntity {
        val existing = dao.getSettingsSync()
        if (existing == null) {
            val defaultSettings = CampaignSettingsEntity()
            dao.insertSettings(defaultSettings)
            return defaultSettings
        }
        return existing
    }

    suspend fun updateSettings(settings: CampaignSettingsEntity) {
        dao.insertSettings(settings)
    }

    suspend fun createParticipant(name: String, period: Period, classRoom: String = "M02"): ParticipantEntity {
        return createOrUpdateParticipant(null, name, period, classRoom)
    }

    suspend fun createOrUpdateParticipant(
        existingId: String?,
        name: String,
        period: Period,
        classRoom: String = "M02"
    ): ParticipantEntity {
        val existing = if (existingId != null) dao.getParticipantById(existingId) else null
        val cleanClass = if (classRoom.isBlank()) "M02" else classRoom.trim().uppercase()
        if (existing != null) {
            val updated = existing.copy(
                name = name.trim(),
                period = period.name,
                classRoom = cleanClass
            )
            dao.insertParticipant(updated)
            return updated
        } else {
            val id = generateParticipantId()
            val participant = ParticipantEntity(
                id = id,
                name = name.trim(),
                period = period.name,
                classRoom = cleanClass,
                score = 0,
                timeSeconds = 0L,
                mistakes = 0,
                completedAt = 0L,
                evidencesFound = "0,0,0,0,0",
                phraseCompleted = false,
                quizCompleted = false
            )
            dao.insertParticipant(participant)
            return participant
        }
    }

    suspend fun markEvidenceFound(participantId: String, evidenceIndex: Int): ParticipantEntity? {
        val participant = dao.getParticipantById(participantId) ?: return null
        val currentList = participant.getEvidenceList().toMutableList()
        if (evidenceIndex in 0..4) {
            currentList[evidenceIndex] = true
        }
        val updatedString = currentList.map { if (it) "1" else "0" }.joinToString(",")
        val updated = participant.copy(evidencesFound = updatedString)
        dao.insertParticipant(updated)
        return updated
    }

    suspend fun markAllEvidencesFound(participantId: String): ParticipantEntity? {
        val participant = dao.getParticipantById(participantId) ?: return null
        val updated = participant.copy(evidencesFound = "1,1,1,1,1")
        dao.insertParticipant(updated)
        return updated
    }

    suspend fun markPhraseCompleted(participantId: String): ParticipantEntity? {
        val participant = dao.getParticipantById(participantId) ?: return null
        val updated = participant.copy(phraseCompleted = true)
        dao.insertParticipant(updated)
        return updated
    }

    suspend fun saveQuizResult(
        participantId: String,
        score: Int,
        timeSeconds: Long,
        mistakes: Int
    ): ParticipantEntity? {
        val participant = dao.getParticipantById(participantId) ?: return null
        val completedAt = System.currentTimeMillis()
        val tempUpdated = participant.copy(
            score = score,
            timeSeconds = timeSeconds,
            mistakes = mistakes,
            completedAt = completedAt,
            quizCompleted = true
        )
        dao.insertParticipant(tempUpdated)

        // Evaluate if participant qualifies for a prize cup
        val finalParticipant = evaluateAndAssignRedemptionCode(tempUpdated)
        return finalParticipant
    }

    suspend fun evaluateAndAssignRedemptionCode(participant: ParticipantEntity): ParticipantEntity {
        val period = Period.fromString(participant.period)
        val rankList = dao.getParticipantsByPeriodSync(period.name)
        val index = rankList.indexOfFirst { it.id == participant.id }

        // If inside prize limit (0 to limit - 1)
        if (index in 0 until period.prizeLimit) {
            if (participant.redemptionCode == null) {
                val code = generateRedemptionCode()
                val withCode = participant.copy(
                    redemptionCode = code,
                    redemptionStatus = "PENDENTE"
                )
                dao.insertParticipant(withCode)
                return withCode
            }
        }
        return participant
    }

    suspend fun freezeAndAssignAllRedemptionCodes() {
        val settings = getSettings()
        dao.insertSettings(settings.copy(isFrozen = true, isOpen = false))

        // Check each period and assign codes to the top 67/67/66
        Period.entries.forEach { period ->
            val list = dao.getParticipantsByPeriodSync(period.name)
            list.take(period.prizeLimit).forEach { p ->
                if (p.redemptionCode == null && p.quizCompleted) {
                    val code = generateRedemptionCode()
                    dao.insertParticipant(
                        p.copy(redemptionCode = code, redemptionStatus = "PENDENTE")
                    )
                }
            }
        }
    }

    suspend fun markCupAsRedeemed(code: String): Boolean {
        val normalized = code.trim().uppercase()
        val participant = dao.getParticipantByRedemptionCode(normalized)
            ?: dao.getParticipantByRedemptionCode(normalized.replace("ESPAT-", "SPAT-"))
            ?: dao.getParticipantByRedemptionCode(normalized.replace("SPAT-", "ESPAT-"))
            ?: return false
        if (participant.redemptionStatus == "RETIRADO") {
            return false // Already redeemed!
        }
        dao.insertParticipant(participant.copy(redemptionStatus = "RETIRADO"))
        return true
    }

    suspend fun getParticipant(id: String): ParticipantEntity? {
        return dao.getParticipantById(id)
    }

    fun getRedeemedCount(period: Period): Flow<Int> {
        return dao.getRedeemedCountByPeriod(period.name)
    }

    fun getTotalRedeemedCount(): Flow<Int> {
        return dao.getTotalRedeemedCount()
    }

    suspend fun resetAllData() {
        dao.clearAllParticipants()
    }

    suspend fun seedDemoParticipantsIfEmpty() {
        val count = dao.getAllParticipantsSync().size
        if (count > 0) return

        val sampleFirstNames = listOf(
            "Gabriel", "Lucas", "Matheus", "Guilherme", "Pedro", "Felipe", "Thiago", "Leonardo",
            "Rodrigo", "Fernando", "Mariana", "Beatriz", "Camila", "Larissa", "Juliana", "Aline",
            "Fernanda", "Amanda", "Leticia", "Bruna", "Gustavo", "Rafael", "Vinicius", "Alexandre",
            "Vitor", "Diego", "Marcelo", "Danilo", "Fabio", "Renato", "Natalia", "Carolina",
            "Patricia", "Renata", "Jessica", "Sabrina", "Vanessa", "Debora", "Monica", "Tatiane"
        )
        val sampleLastNames = listOf(
            "Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Ferreira", "Alves", "Pereira",
            "Lima", "Gomes", "Costa", "Ribeiro", "Martins", "Carvalho", "Almeida", "Lopes",
            "Soares", "Fernandes", "Vieira", "Barbosa", "Rocha", "Dias", "Nascimento", "Andrade"
        )

        val demoList = mutableListOf<ParticipantEntity>()

        // Generate ~70 participants for MANHA, ~70 for TARDE, ~70 for NOITE
        Period.entries.forEach { period ->
            val totalForPeriod = if (period == Period.NOITE) 68 else 70
            for (i in 1..totalForPeriod) {
                val fName = sampleFirstNames[Random.nextInt(sampleFirstNames.size)]
                val lName = sampleLastNames[Random.nextInt(sampleLastNames.size)]
                val name = "$fName $lName"
                val score = Random.nextInt(18, 30) * 150 // Realistic high scores
                val time = Random.nextLong(120, 600)
                val mistakes = Random.nextInt(0, 5)
                val completedAt = System.currentTimeMillis() - Random.nextLong(10000, 86400000)

                val isInPrizeZone = i <= period.prizeLimit
                val code = if (isInPrizeZone) generateRedemptionCode() else null
                val status = if (isInPrizeZone && i % 3 == 0) "RETIRADO" else "PENDENTE"

                demoList.add(
                    ParticipantEntity(
                        id = "SPAT-${Random.nextInt(1000, 9999)}",
                        name = name,
                        period = period.name,
                        classRoom = "M02",
                        score = score,
                        timeSeconds = time,
                        mistakes = mistakes,
                        completedAt = completedAt,
                        evidencesFound = "1,1,1,1,1",
                        phraseCompleted = true,
                        quizCompleted = true,
                        redemptionCode = code,
                        redemptionStatus = status
                    )
                )
            }
        }

        dao.insertParticipants(demoList)
    }

    private fun generateParticipantId(): String {
        val rand = Random.nextInt(1000, 9999)
        return "SPAT-U$rand"
    }

    private fun generateRedemptionCode(): String {
        val chars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ"
        val part1 = (1..2).map { chars.random() }.joinToString("")
        val part2 = (1..2).map { chars.random() }.joinToString("")
        return "SPAT-$part1$part2"
    }
}
