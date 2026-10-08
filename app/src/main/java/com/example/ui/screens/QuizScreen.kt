package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Difficulty
import com.example.data.model.QuestionBank
import com.example.ui.components.HazardStrip
import com.example.ui.theme.CharcoalBlack
import com.example.ui.theme.DangerRed
import com.example.ui.theme.HazardBlack
import com.example.ui.theme.IndustrialGray
import com.example.ui.theme.PrizeGreen
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyYellow
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(
    participantName: String,
    onFinishQuiz: (score: Int, timeSeconds: Long, mistakes: Int) -> Unit,
    onCancelQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDifficultyFilter by remember { mutableStateOf<Difficulty?>(null) }

    val questions = remember(selectedDifficultyFilter) {
        val pool = if (selectedDifficultyFilter == null) {
            QuestionBank.questions
        } else {
            QuestionBank.questions.filter { it.difficulty == selectedDifficultyFilter }
        }
        pool.shuffled()
    }
    val totalQuestions = questions.size

    var currentIndex by remember { mutableIntStateOf(0) }
    var currentScore by remember { mutableIntStateOf(0) }
    var mistakesCount by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }

    var elapsedSeconds by remember { mutableLongStateOf(0L) }
    var isTimerRunning by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()

    // Timer coroutine
    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning) {
            delay(1000L)
            elapsedSeconds += 1L
        }
    }

    val currentQuestion = questions.getOrElse(currentIndex) { questions.first() }

    // Randomize options every time the question is accessed
    val currentShuffledOptions = remember(currentQuestion.id, currentIndex, selectedDifficultyFilter) {
        currentQuestion.options.shuffled()
    }
    val correctIndexInShuffled = remember(currentQuestion.id, currentIndex, currentShuffledOptions) {
        val originalCorrect = currentQuestion.options[currentQuestion.correctIndex]
        currentShuffledOptions.indexOf(originalCorrect)
    }

    val formattedTime = remember(elapsedSeconds) {
        val minutes = elapsedSeconds / 60
        val seconds = elapsedSeconds % 60
        String.format("%02d:%02d", minutes, seconds)
    }

    fun submitAnswer() {
        if (selectedOption == null) return
        isAnswerSubmitted = true

        val isCorrect = selectedOption == correctIndexInShuffled
        if (isCorrect) {
            currentScore += currentQuestion.difficulty.points
        } else {
            // Section 12: 0 pontos, NÃO SUBTRAIR PONTOS
            mistakesCount += 1
        }
    }

    fun nextQuestion() {
        if (currentIndex < totalQuestions - 1) {
            currentIndex += 1
            selectedOption = null
            isAnswerSubmitted = false
        } else {
            // Quiz completed!
            isTimerRunning = false
            onFinishQuiz(currentScore, elapsedSeconds, mistakesCount)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CharcoalBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 32.dp)
        ) {
            HazardStrip(heightDp = 8)

            // Top Status Bar: Timer, Score, Progress
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCancelQuiz) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancelar",
                        tint = IndustrialGray
                    )
                }

                // Timer badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceCard)
                        .border(1.dp, SurfaceCardBorder, RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = SafetyYellow,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = formattedTime,
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            color = SafetyYellow,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // Score badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SafetyYellow)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$currentScore PTS",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        color = HazardBlack,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            // Progress bar
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / totalQuestions.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = SafetyYellow,
                trackColor = SurfaceCard
            )

            // Difficulty Mode Selector Tabs (Fácil, Médio, Difícil, Todas)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val modes = listOf<Pair<String, Difficulty?>>(
                    "TODAS" to null,
                    "FÁCIL" to Difficulty.FACIL,
                    "MÉDIO" to Difficulty.MEDIA,
                    "DIFÍCIL" to Difficulty.DIFICIL
                )
                modes.forEach { (label, diff) ->
                    val isSelected = selectedDifficultyFilter == diff
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) SafetyYellow else SurfaceCard)
                            .border(
                                1.dp,
                                if (isSelected) SafetyYellow else SurfaceCardBorder,
                                RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                if (selectedDifficultyFilter != diff) {
                                    selectedDifficultyFilter = diff
                                    currentIndex = 0
                                    currentScore = 0
                                    mistakesCount = 0
                                    selectedOption = null
                                    isAnswerSubmitted = false
                                }
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isSelected) HazardBlack else TextWhite
                        )
                    }
                }
            }

            // Question Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SurfaceCardBorder)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PERGUNTA ${currentIndex + 1} DE $totalQuestions",
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp,
                            color = IndustrialGray,
                            letterSpacing = 1.sp
                        )

                        // Difficulty Tag
                        val diffColor = when (currentQuestion.difficulty) {
                            Difficulty.FACIL -> PrizeGreen
                            Difficulty.MEDIA -> SafetyAmber
                            Difficulty.DIFICIL -> DangerRed
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(diffColor.copy(alpha = 0.2f))
                                .border(1.dp, diffColor, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${currentQuestion.difficulty.label.uppercase()} (+${currentQuestion.difficulty.points} pts)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = diffColor
                            )
                        }
                    }

                    // Category tag
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(HazardBlack)
                            .border(1.dp, SurfaceCardBorder, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "TEMA: ${currentQuestion.category.uppercase()}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyYellow,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Scenario
                    Text(
                        text = currentQuestion.scenario,
                        fontSize = 13.sp,
                        color = IndustrialGray,
                        lineHeight = 18.sp
                    )

                    // Prompt
                    Text(
                        text = currentQuestion.prompt,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        lineHeight = 20.sp
                    )
                }
            }

            // Options List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                currentShuffledOptions.forEachIndexed { idx, optionText ->
                    val isSelected = selectedOption == idx
                    val isCorrect = idx == correctIndexInShuffled

                    val bgColor = when {
                        isAnswerSubmitted && isCorrect -> PrizeGreen.copy(alpha = 0.2f)
                        isAnswerSubmitted && isSelected && !isCorrect -> DangerRed.copy(alpha = 0.2f)
                        isSelected -> SafetyYellow.copy(alpha = 0.2f)
                        else -> SurfaceCard
                    }

                    val borderColor = when {
                        isAnswerSubmitted && isCorrect -> PrizeGreen
                        isAnswerSubmitted && isSelected && !isCorrect -> DangerRed
                        isSelected -> SafetyYellow
                        else -> SurfaceCardBorder
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(bgColor)
                            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                            .clickable(enabled = !isAnswerSubmitted) {
                                selectedOption = idx
                            }
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isAnswerSubmitted && isCorrect -> PrizeGreen
                                            isAnswerSubmitted && isSelected && !isCorrect -> DangerRed
                                            isSelected -> SafetyYellow
                                            else -> SurfaceDark
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A' + idx).toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = if (isSelected || (isAnswerSubmitted && isCorrect)) HazardBlack else TextWhite
                                )
                            }

                            Text(
                                text = optionText,
                                fontSize = 13.sp,
                                color = TextWhite,
                                modifier = Modifier.weight(1f),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Explanation & Next Action
            if (isAnswerSubmitted) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            if (selectedOption == correctIndexInShuffled) PrizeGreen else DangerRed
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (selectedOption == correctIndexInShuffled) "RESPOSTA CORRETA (+${currentQuestion.difficulty.points} PTS)" else "RESPOSTA INCORRETA (0 PTS)",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            color = if (selectedOption == correctIndexInShuffled) PrizeGreen else DangerRed
                        )
                        Text(
                            text = currentQuestion.explanation,
                            fontSize = 12.sp,
                            color = TextWhite,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Submit / Next Question Button
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                if (!isAnswerSubmitted) {
                    Button(
                        onClick = { submitAnswer() },
                        enabled = selectedOption != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SafetyYellow,
                            contentColor = HazardBlack,
                            disabledContainerColor = SurfaceCard,
                            disabledContentColor = IndustrialGray
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("confirmar_resposta_button")
                    ) {
                        Text(
                            text = "CONFIRMAR RESPOSTA",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp
                        )
                    }
                } else {
                    Button(
                        onClick = { nextQuestion() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SafetyYellow,
                            contentColor = HazardBlack
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("proxima_pergunta_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = if (currentIndex < totalQuestions - 1) "PRÓXIMA PERGUNTA" else "FINALIZAR INVESTIGAÇÃO",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
