package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participants")
data class ParticipantEntity(
    @PrimaryKey
    val id: String, // e.g., "ESPAT-U8492"
    val name: String,
    val period: String, // "MANHA", "TARDE", "NOITE"
    val classRoom: String = "M02",
    val score: Int = 0,
    val timeSeconds: Long = 0L,
    val mistakes: Int = 0,
    val completedAt: Long = 0L,
    val evidencesFound: String = "0,0,0,0,0", // 1/0 comma separated
    val phraseCompleted: Boolean = false,
    val quizCompleted: Boolean = false,
    val redemptionCode: String? = null, // e.g., "ESPAT-7K42"
    val redemptionStatus: String = "PENDENTE" // "PENDENTE" or "RETIRADO"
) {
    fun getEvidenceList(): List<Boolean> {
        val parts = evidencesFound.split(",")
        return (0..4).map { idx ->
            parts.getOrNull(idx) == "1"
        }
    }

    fun isAllEvidencesFound(): Boolean {
        return getEvidenceList().all { it }
    }
}
