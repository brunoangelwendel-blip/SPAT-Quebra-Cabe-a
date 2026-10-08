package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EvidenceData
import com.example.ui.components.HazardBanner
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhraseAssemblyScreen(
    onPhraseCompleted: () -> Unit,
    onBack: () -> Unit,
    onLiberarDesafio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Scrambled initial word order
    val initialPool = remember {
        listOf("CONSTRUÍMOS", "SEGURANÇA", "JOGANDO", "NOSSA", "JUNTOS")
    }

    // Ordered user slots (up to 5 words placed)
    var placedWords by remember { mutableStateOf<List<String>>(emptyList()) }
    var availablePool by remember { mutableStateOf(initialPool) }

    var isChecked by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val correctWords = EvidenceData.OFFICIAL_PHRASE_WORDS // ["JOGANDO", "JUNTOS", "CONSTRUÍMOS", "NOSSA", "SEGURANÇA"]

    fun checkPhrase() {
        isChecked = true
        if (placedWords.size == 5 && placedWords == correctWords) {
            isSuccess = true
            errorMessage = null
            onPhraseCompleted()
        } else {
            isSuccess = false
            errorMessage = "A mensagem ainda não está completa. Reorganize as peças e tente novamente."
        }
    }

    fun resetWords() {
        placedWords = emptyList()
        availablePool = initialPool
        isChecked = false
        isSuccess = false
        errorMessage = null
    }

    fun swapPlaced(indexA: Int, indexB: Int) {
        if (indexA in placedWords.indices && indexB in placedWords.indices) {
            val newList = placedWords.toMutableList()
            val temp = newList[indexA]
            newList[indexA] = newList[indexB]
            newList[indexB] = temp
            placedWords = newList
            isChecked = false
            errorMessage = null
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

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("phrase_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = SafetyYellow
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "MONTAGEM DO QUEBRA-CABEÇA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialGray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "ORGANIZAR A FRASE DA CAMPANHA",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = TextWhite
                    )
                }

                IconButton(
                    onClick = { resetWords() },
                    modifier = Modifier.testTag("reset_phrase_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reiniciar",
                        tint = IndustrialGray
                    )
                }
            }

            // Instructions Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SurfaceCardBorder)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Extension,
                            contentDescription = null,
                            tint = SafetyYellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "DESVENDAR A MENSAGEM ESCONDIDA",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = SafetyYellow
                        )
                    }

                    Text(
                        text = "Você encontrou as 5 peças do quebra-cabeça. Toque nas palavras abaixo para colocá-las nos slots ou reposicioná-las na ordem correta da campanha oficial.",
                        fontSize = 12.sp,
                        color = IndustrialGray,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // SLOTS AREA (Frase Sendo Montada)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        if (isSuccess) PrizeGreen else SafetyYellow
                    )
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
                            text = "SUA FRASE MONTADA (${placedWords.size}/5):",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyYellow,
                            letterSpacing = 0.5.sp
                        )

                        if (placedWords.isNotEmpty() && !isSuccess) {
                            Text(
                                text = "Toque na palavra para remover",
                                fontSize = 10.sp,
                                color = IndustrialGray
                            )
                        }
                    }

                    // 5 Slots vertical rows with reorder arrows
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in 0..4) {
                            val word = placedWords.getOrNull(i)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (word != null) SurfaceCard else HazardBlack)
                                    .border(
                                        width = 1.dp,
                                        color = when {
                                            isSuccess -> PrizeGreen
                                            word != null -> SafetyYellow
                                            else -> SurfaceCardBorder
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Text(
                                            text = "#${i + 1}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = IndustrialGray,
                                            fontFamily = FontFamily.Monospace
                                        )

                                        if (word != null) {
                                            Text(
                                                text = word,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Black,
                                                color = if (isSuccess) PrizeGreen else SafetyYellow,
                                                fontFamily = FontFamily.Monospace,
                                                modifier = Modifier.clickable(enabled = !isSuccess) {
                                                    // Return word back to pool
                                                    placedWords = placedWords.filterIndexed { index, _ -> index != i }
                                                    availablePool = availablePool + word
                                                    isChecked = false
                                                    errorMessage = null
                                                }
                                            )
                                        } else {
                                            Text(
                                                text = "Slot vazio (toque em uma palavra)",
                                                fontSize = 12.sp,
                                                color = IndustrialGray
                                            )
                                        }
                                    }

                                    // Up/Down reordering controls
                                    if (word != null && !isSuccess) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                            if (i > 0) {
                                                IconButton(
                                                    onClick = { swapPlaced(i, i - 1) },
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.ArrowUpward,
                                                        contentDescription = "Mover para cima",
                                                        tint = SafetyYellow,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                            if (i < placedWords.size - 1) {
                                                IconButton(
                                                    onClick = { swapPlaced(i, i + 1) },
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.ArrowDownward,
                                                        contentDescription = "Mover para baixo",
                                                        tint = SafetyYellow,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // AVAILABLE POOL OF WORDS
            if (availablePool.isNotEmpty() && !isSuccess) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                        Text(
                            text = "PALAVRAS DISPONÍVEIS:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialGray,
                            letterSpacing = 0.5.sp
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            availablePool.forEach { word ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SafetyYellow)
                                        .clickable {
                                            if (placedWords.size < 5) {
                                                placedWords = placedWords + word
                                                availablePool = availablePool.filter { it != word }
                                                isChecked = false
                                                errorMessage = null
                                            }
                                        }
                                        .padding(horizontal = 14.dp, vertical = 10.dp)
                                ) {
                                    Text(
                                        text = "+ $word",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 13.sp,
                                        color = HazardBlack,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // VERIFICATION BUTTON
            if (!isSuccess) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { checkPhrase() },
                        enabled = placedWords.size == 5,
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
                            .testTag("validar_frase_button")
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "VALIDAR ORDEM DA FRASE",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp
                        )
                    }

                    // Section 9: Error feedback
                    if (errorMessage != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = DangerRed.copy(alpha = 0.15f)),
                            border = CardDefaults.outlinedCardBorder().copy(
                                brush = androidx.compose.ui.graphics.SolidColor(DangerRed)
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "A mensagem ainda não está completa.",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = DangerRed
                                )
                                Text(
                                    text = "Reorganize as peças e tente novamente.",
                                    fontSize = 12.sp,
                                    color = TextWhite
                                )
                            }
                        }
                    }
                }
            }

            // SUCCESS STATE (Mandatory Section 8)
            AnimatedVisibility(
                visible = isSuccess,
                enter = fadeIn() + slideInVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("puzzle_completed_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = HazardBlack),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(PrizeGreen)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = PrizeGreen,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "🧩 QUEBRA-CABEÇA COMPLETO",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp,
                                    color = PrizeGreen,
                                    letterSpacing = 1.sp
                                )
                            }

                            Text(
                                text = "Você encontrou todas as peças.",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SafetyYellow)
                                    .padding(14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = EvidenceData.OFFICIAL_FULL_PHRASE,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp,
                                    color = HazardBlack,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 22.sp
                                )
                            }

                            Text(
                                text = "Agora começa a verdadeira investigação.",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = IndustrialGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Button: LIBERAR DESAFIO (Mandatory Section 8)
                    Button(
                        onClick = onLiberarDesafio,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SafetyYellow,
                            contentColor = HazardBlack
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("liberar_desafio_button")
                    ) {
                        Text(
                            text = "LIBERAR DESAFIO",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HazardBanner(text = "SENAC VILA PRUDENTE • TURMA M02 • SST")
        }
    }
}
