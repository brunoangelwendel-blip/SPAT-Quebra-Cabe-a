package com.example.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.ParticipantEntity
import com.example.data.model.EvidenceData
import com.example.data.model.Period
import com.example.ui.components.QRCodeScannerDialog
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.EvidenceScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PhraseAssemblyScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.RankingsScreen
import com.example.ui.screens.RegistrationScreen
import com.example.ui.screens.ResultScreen
import com.example.ui.theme.CharcoalBlack
import com.example.ui.viewmodel.EspatViewModel

enum class Screen {
    HOME,
    EVIDENCE,
    PHRASE_ASSEMBLY,
    REGISTRATION,
    QUIZ,
    RESULT,
    RANKINGS,
    ADMIN,
    ABOUT
}

@Composable
fun EspatApp(
    viewModel: EspatViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }
    var selectedEvidenceId by remember { mutableIntStateOf(1) }
    var showQrDialog by remember { mutableStateOf(false) }

    val currentParticipant by viewModel.currentParticipant.collectAsState()
    val allParticipants by viewModel.allParticipants.collectAsState()
    val settings by viewModel.settingsFlow.collectAsState()

    val redeemedMorning by viewModel.redeemedMorning.collectAsState()
    val redeemedAfternoon by viewModel.redeemedAfternoon.collectAsState()
    val redeemedNight by viewModel.redeemedNight.collectAsState()
    val totalRedeemed by viewModel.totalRedeemed.collectAsState()

    var lastFinishedParticipant by remember { mutableStateOf<ParticipantEntity?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CharcoalBlack
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)

        when (currentScreen) {
            Screen.HOME -> {
                HomeScreen(
                    currentParticipant = currentParticipant,
                    redeemedMorning = redeemedMorning,
                    redeemedAfternoon = redeemedAfternoon,
                    redeemedNight = redeemedNight,
                    totalRedeemed = totalRedeemed,
                    onStartInvestigation = {
                        selectedEvidenceId = 1
                        currentScreen = Screen.EVIDENCE
                    },
                    onOpenScanner = {
                        showQrDialog = true
                    },
                    onSelectEvidence = { evidenceId ->
                        selectedEvidenceId = evidenceId
                        currentScreen = Screen.EVIDENCE
                    },
                    onNavigateToAssembly = {
                        currentScreen = Screen.PHRASE_ASSEMBLY
                    },
                    onNavigateToQuiz = {
                        if (currentParticipant == null) {
                            currentScreen = Screen.REGISTRATION
                        } else {
                            currentScreen = Screen.QUIZ
                        }
                    },
                    onNavigateToRankings = {
                        currentScreen = Screen.RANKINGS
                    },
                    onNavigateToAbout = {
                        currentScreen = Screen.ABOUT
                    },
                    onNavigateToAdmin = {
                        currentScreen = Screen.ADMIN
                    },
                    modifier = screenModifier
                )
            }

            Screen.EVIDENCE -> {
                val evidence = EvidenceData.evidences.firstOrNull { it.id == selectedEvidenceId }
                    ?: EvidenceData.evidences.first()
                val isFound = currentParticipant?.getEvidenceList()?.getOrNull(evidence.id - 1) ?: false

                EvidenceScreen(
                    evidence = evidence,
                    isAlreadyFound = isFound,
                    onEvidenceFound = { index ->
                        viewModel.markEvidenceFound(index)
                    },
                    onBack = {
                        currentScreen = Screen.HOME
                    },
                    onNavigateToNext = {
                        if (selectedEvidenceId < 5) {
                            selectedEvidenceId += 1
                        } else {
                            currentScreen = Screen.PHRASE_ASSEMBLY
                        }
                    },
                    modifier = screenModifier
                )
            }

            Screen.PHRASE_ASSEMBLY -> {
                PhraseAssemblyScreen(
                    onPhraseCompleted = {
                        viewModel.markPhraseCompleted()
                    },
                    onBack = {
                        currentScreen = Screen.HOME
                    },
                    onLiberarDesafio = {
                        // Always prompt user to confirm/choose Period, Name, and Turma before entering the second game
                        currentScreen = Screen.REGISTRATION
                    },
                    modifier = screenModifier
                )
            }

            Screen.REGISTRATION -> {
                RegistrationScreen(
                    initialName = currentParticipant?.name?.takeIf { it != "Investigador(a)" } ?: "",
                    initialPeriod = currentParticipant?.period?.let { Period.fromString(it) } ?: Period.MANHA,
                    initialClassRoom = currentParticipant?.classRoom ?: "M02",
                    onRegister = { name, period, classRoom ->
                        viewModel.startParticipant(name, period, classRoom) {
                            currentScreen = Screen.QUIZ
                        }
                    },
                    onBack = {
                        currentScreen = Screen.HOME
                    },
                    modifier = screenModifier
                )
            }

            Screen.QUIZ -> {
                QuizScreen(
                    participantName = currentParticipant?.name ?: "Investigador",
                    onFinishQuiz = { score, timeSeconds, mistakes ->
                        viewModel.submitQuizResult(score, timeSeconds, mistakes) { finished ->
                            lastFinishedParticipant = finished
                            currentScreen = Screen.RESULT
                        }
                    },
                    onCancelQuiz = {
                        currentScreen = Screen.HOME
                    },
                    modifier = screenModifier
                )
            }

            Screen.RESULT -> {
                val participant = lastFinishedParticipant ?: currentParticipant
                if (participant != null) {
                    val period = Period.fromString(participant.period)
                    val periodParticipants = allParticipants.filter { it.period == period.name && it.quizCompleted }
                    val rankIndex = periodParticipants.indexOfFirst { it.id == participant.id }
                    val currentRank = if (rankIndex >= 0) rankIndex + 1 else 1

                    ResultScreen(
                        participant = participant,
                        currentRank = currentRank,
                        periodLimit = period.prizeLimit,
                        onNavigateToHome = {
                            currentScreen = Screen.HOME
                        },
                        onNavigateToRankings = {
                            currentScreen = Screen.RANKINGS
                        },
                        modifier = screenModifier
                    )
                } else {
                    currentScreen = Screen.HOME
                }
            }

            Screen.RANKINGS -> {
                RankingsScreen(
                    allParticipants = allParticipants,
                    onBack = {
                        currentScreen = Screen.HOME
                    },
                    modifier = screenModifier
                )
            }

            Screen.ADMIN -> {
                AdminScreen(
                    settings = settings,
                    participants = allParticipants,
                    redeemedMorning = redeemedMorning,
                    redeemedAfternoon = redeemedAfternoon,
                    redeemedNight = redeemedNight,
                    totalRedeemed = totalRedeemed,
                    onToggleCompetition = { isClosed ->
                        viewModel.toggleCompetition(isClosed)
                    },
                    onRedeemCode = { code, callback ->
                        viewModel.redeemCupCode(code, callback)
                    },
                    onResetDatabase = {
                        viewModel.resetDatabase()
                    },
                    onBack = {
                        currentScreen = Screen.HOME
                    },
                    modifier = screenModifier
                )
            }

            Screen.ABOUT -> {
                AboutScreen(
                    onBack = {
                        currentScreen = Screen.HOME
                    },
                    modifier = screenModifier
                )
            }
        }
    }

    // QR Code scanner dialog
    if (showQrDialog) {
        QRCodeScannerDialog(
            onDismiss = { showQrDialog = false },
            onEvidenceScanned = { evidenceId ->
                viewModel.markEvidenceFound(evidenceId - 1)
                selectedEvidenceId = evidenceId
                currentScreen = Screen.EVIDENCE
            }
        )
    }
}
