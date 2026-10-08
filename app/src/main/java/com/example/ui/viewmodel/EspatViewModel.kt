package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.CampaignSettingsEntity
import com.example.data.local.EspatDatabase
import com.example.data.local.ParticipantEntity
import com.example.data.model.Period
import com.example.data.repository.EspatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EspatViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EspatRepository

    val allParticipants: StateFlow<List<ParticipantEntity>>
    val settingsFlow: StateFlow<CampaignSettingsEntity>

    val redeemedMorning: StateFlow<Int>
    val redeemedAfternoon: StateFlow<Int>
    val redeemedNight: StateFlow<Int>
    val totalRedeemed: StateFlow<Int>

    private val _currentParticipant = MutableStateFlow<ParticipantEntity?>(null)
    val currentParticipant: StateFlow<ParticipantEntity?> = _currentParticipant.asStateFlow()

    init {
        val database = EspatDatabase.getDatabase(application)
        repository = EspatRepository(database.espatDao())

        allParticipants = repository.allParticipants.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        settingsFlow = repository.settingsFlow
            .map { it ?: CampaignSettingsEntity() }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                CampaignSettingsEntity()
            )

        redeemedMorning = repository.getRedeemedCount(Period.MANHA).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

        redeemedAfternoon = repository.getRedeemedCount(Period.TARDE).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

        redeemedNight = repository.getRedeemedCount(Period.NOITE).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

        totalRedeemed = repository.getTotalRedeemedCount().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )
    }

    fun startParticipant(
        name: String,
        period: Period,
        classRoom: String = "M02",
        onComplete: (ParticipantEntity) -> Unit
    ) {
        viewModelScope.launch {
            val existing = _currentParticipant.value
            val participant = repository.createOrUpdateParticipant(
                existingId = existing?.id,
                name = name,
                period = period,
                classRoom = classRoom
            )
            _currentParticipant.value = participant
            onComplete(participant)
        }
    }

    fun markEvidenceFound(evidenceIndex: Int) {
        val participant = _currentParticipant.value
        viewModelScope.launch {
            if (participant != null) {
                val updated = repository.markEvidenceFound(participant.id, evidenceIndex)
                _currentParticipant.value = updated
            } else {
                // If participant not yet created, create anonymous or temporary session
                val temp = repository.createParticipant("Investigador(a)", Period.MANHA)
                val updated = repository.markEvidenceFound(temp.id, evidenceIndex)
                _currentParticipant.value = updated
            }
        }
    }

    fun markAllEvidencesFound() {
        val participant = _currentParticipant.value ?: return
        viewModelScope.launch {
            val updated = repository.markAllEvidencesFound(participant.id)
            _currentParticipant.value = updated
        }
    }

    fun markPhraseCompleted() {
        val participant = _currentParticipant.value ?: return
        viewModelScope.launch {
            val updated = repository.markPhraseCompleted(participant.id)
            _currentParticipant.value = updated
        }
    }

    fun submitQuizResult(
        score: Int,
        timeSeconds: Long,
        mistakes: Int,
        onFinished: (ParticipantEntity) -> Unit
    ) {
        val participant = _currentParticipant.value ?: return
        viewModelScope.launch {
            val updated = repository.saveQuizResult(participant.id, score, timeSeconds, mistakes)
            if (updated != null) {
                _currentParticipant.value = updated
                onFinished(updated)
            }
        }
    }

    fun toggleCompetition(isClosed: Boolean) {
        viewModelScope.launch {
            if (isClosed) {
                repository.freezeAndAssignAllRedemptionCodes()
            } else {
                val s = repository.getSettings()
                repository.updateSettings(s.copy(isFrozen = false, isOpen = true))
            }
        }
    }

    fun redeemCupCode(code: String, onResult: (Boolean, ParticipantEntity?) -> Unit) {
        viewModelScope.launch {
            val database = EspatDatabase.getDatabase(getApplication())
            val participant = database.espatDao().getParticipantByRedemptionCode(code.trim().uppercase())
            if (participant == null) {
                onResult(false, null)
                return@launch
            }
            if (participant.redemptionStatus == "RETIRADO") {
                onResult(false, participant)
                return@launch
            }
            val success = repository.markCupAsRedeemed(code)
            val updated = database.espatDao().getParticipantById(participant.id)
            onResult(success, updated)
        }
    }

    fun resetDatabase() {
        viewModelScope.launch {
            repository.resetAllData()
            _currentParticipant.value = null
        }
    }
}
