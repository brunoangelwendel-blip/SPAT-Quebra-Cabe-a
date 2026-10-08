package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Evidence
import com.example.ui.components.HazardBanner
import com.example.ui.components.HazardStrip
import com.example.ui.theme.CharcoalBlack
import com.example.ui.theme.HazardBlack
import com.example.ui.theme.IndustrialGray
import com.example.ui.theme.PrizeGreen
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyYellow
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextWhite

@Composable
fun EvidenceScreen(
    evidence: Evidence,
    isAlreadyFound: Boolean,
    onEvidenceFound: (Int) -> Unit,
    onBack: () -> Unit,
    onNavigateToNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var revealed by remember { mutableStateOf(isAlreadyFound) }
    var feedbackMessage by remember { mutableStateOf<String?>(null) }

    // Shuffle options dynamically so the correct answer is randomly positioned every time
    val shuffledOptions = remember(evidence.id) {
        evidence.inspectionOptions.shuffled()
    }
    val correctInspectionOptionIndex = remember(evidence.id, shuffledOptions) {
        val originalCorrectText = evidence.inspectionOptions[evidence.correctInspectionOptionIndex]
        shuffledOptions.indexOf(originalCorrectText)
    }

    val pieceOrdinal = when (evidence.id) {
        1 -> "primeira"
        2 -> "segunda"
        3 -> "terceira"
        4 -> "quarta"
        5 -> "quinta"
        else -> "${evidence.id}ª"
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
                    modifier = Modifier.testTag("evidence_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = SafetyYellow
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "DOSSIÊ PERICIAL DE SST",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialGray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${evidence.title} • ${evidence.subtitle}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = TextWhite
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(SafetyYellow)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "QR 0${evidence.id}",
                        fontWeight = FontWeight.Black,
                        fontSize = 11.sp,
                        color = HazardBlack,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            // Evidence Photo with Marker
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SurfaceCardBorder)
                )
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = evidence.imageResId),
                            contentDescription = evidence.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Evidence Marker Pin
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(HazardBlack.copy(alpha = 0.9f))
                                .border(1.dp, SafetyYellow, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "EVIDÊNCIA #0${evidence.id}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = SafetyYellow,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    // Narrative section
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = SafetyYellow,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "RELATÓRIO DO LOCAL DO ACIDENTE",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = SafetyYellow,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Text(
                            text = evidence.narrative,
                            fontSize = 13.sp,
                            color = TextWhite,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Interactive Investigation Element
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        if (revealed) PrizeGreen else SafetyYellow
                    )
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (revealed) PrizeGreen else SafetyYellow),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (revealed) Icons.Default.CheckCircle else Icons.Default.Warning,
                                contentDescription = null,
                                tint = HazardBlack,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Text(
                            text = if (revealed) "INVESTIGAÇÃO CONCLUÍDA" else "ETAPA DE INVESTIGAÇÃO INTERATIVA",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = if (revealed) PrizeGreen else SafetyYellow
                        )
                    }

                    Text(
                        text = evidence.inspectionQuestion,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite,
                        lineHeight = 18.sp
                    )

                    // Options
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        shuffledOptions.forEachIndexed { idx, opt ->
                            val isSelected = selectedOption == idx
                            val isCorrect = idx == correctInspectionOptionIndex

                            val bgColor = when {
                                revealed && isCorrect -> PrizeGreen.copy(alpha = 0.2f)
                                isSelected && !isCorrect -> SafetyAmber.copy(alpha = 0.2f)
                                isSelected -> SafetyYellow.copy(alpha = 0.2f)
                                else -> SurfaceCard
                            }

                            val borderColor = when {
                                revealed && isCorrect -> PrizeGreen
                                isSelected && !isCorrect -> SafetyAmber
                                isSelected -> SafetyYellow
                                else -> SurfaceCardBorder
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(bgColor)
                                    .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                                    .clickable(enabled = !revealed) {
                                        selectedOption = idx
                                        if (idx == correctInspectionOptionIndex) {
                                            revealed = true
                                            feedbackMessage = "Correto! Evidência periciada com sucesso."
                                            onEvidenceFound(evidence.id - 1)
                                        } else {
                                            feedbackMessage = "Conduta inadequada para SST. Reavalie os riscos e tente novamente."
                                        }
                                    }
                                    .padding(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(if (revealed && isCorrect) PrizeGreen else SurfaceDark)
                                            .border(1.dp, borderColor, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = ('A' + idx).toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = if (revealed && isCorrect) HazardBlack else TextWhite
                                        )
                                    }

                                    Text(
                                        text = opt,
                                        fontSize = 12.sp,
                                        color = TextWhite,
                                        modifier = Modifier.weight(1f),
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    if (feedbackMessage != null && !revealed) {
                        Text(
                            text = feedbackMessage!!,
                            color = SafetyAmber,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // REVEALED PIECE & OFFICIAL WORD (Mandatory Section 6)
            AnimatedVisibility(
                visible = revealed,
                enter = fadeIn() + slideInVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("piece_found_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = HazardBlack),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(SafetyYellow)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Extension,
                                    contentDescription = null,
                                    tint = SafetyYellow,
                                    modifier = Modifier.size(22.dp)
                                )
                                Text(
                                    text = "🧩 PEÇA ENCONTRADA",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp,
                                    color = SafetyYellow,
                                    letterSpacing = 1.sp
                                )
                            }

                            // Big Highlighted Word
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SafetyYellow)
                                    .padding(horizontal = 24.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = evidence.word,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 26.sp,
                                    color = HazardBlack,
                                    letterSpacing = 2.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            Text(
                                text = "Você encontrou a $pieceOrdinal peça.",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )

                            Text(
                                text = "Guarde esta palavra. Ela é parte da frase oficial de 5 palavras do quebra-cabeça da SPAT.",
                                fontSize = 11.sp,
                                color = IndustrialGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Workplace Safety / NR Information Box
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(SurfaceCardBorder)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = SafetyYellow,
                                modifier = Modifier.size(20.dp)
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "INFORMAÇÃO TÉCNICA DE SST",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = SafetyYellow,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = evidence.nrInformation,
                                    fontSize = 12.sp,
                                    color = TextWhite,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SafetyYellow,
                            contentColor = HazardBlack
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("voltar_painel_button")
                    ) {
                        Text(
                            text = "← VOLTAR AO PAINEL DE INVESTIGAÇÃO",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HazardBanner(text = "UMA PEÇA SOZINHA NÃO COMPLETA O QUEBRA-CABEÇA")
        }
    }
}
