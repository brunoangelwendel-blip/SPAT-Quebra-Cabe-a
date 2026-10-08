package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EvidenceData
import com.example.ui.theme.HazardBlack
import com.example.ui.theme.IndustrialGray
import com.example.ui.theme.MutedGray
import com.example.ui.theme.PrizeGreen
import com.example.ui.theme.SafetyYellow
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextWhite

@Composable
fun PuzzleProgressCard(
    evidencesFound: List<Boolean>,
    onSelectEvidence: (Int) -> Unit,
    onNavigateToAssembly: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalFound = evidencesFound.count { it }
    val allFound = totalFound == 5

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("puzzle_progress_card"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(
                if (allFound) SafetyYellow else SurfaceCardBorder
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Extension,
                        contentDescription = "Quebra-Cabeça",
                        tint = SafetyYellow
                    )
                    Text(
                        text = "PEÇAS ENCONTRADAS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp,
                        color = TextWhite
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (allFound) PrizeGreen else SurfaceCard)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$totalFound / 5",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = if (allFound) HazardBlack else SafetyYellow
                    )
                }
            }

            // 5 Piece Slots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                for (i in 0..4) {
                    val isFound = evidencesFound.getOrElse(i) { false }
                    val word = EvidenceData.OFFICIAL_PHRASE_WORDS[i]
                    val pieceNumber = String.format("%02d", i + 1)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isFound) SurfaceCard else HazardBlack)
                            .border(
                                width = 1.dp,
                                color = if (isFound) SafetyYellow else SurfaceCardBorder,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onSelectEvidence(i + 1) }
                            .padding(vertical = 8.dp, horizontal = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "🧩 $pieceNumber",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isFound) TextWhite else MutedGray,
                                fontFamily = FontFamily.Monospace
                            )

                            if (isFound) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Encontrada",
                                    tint = PrizeGreen,
                                    modifier = Modifier.height(14.dp)
                                )
                                Text(
                                    text = word,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = SafetyYellow,
                                    maxLines = 1
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Bloqueada",
                                    tint = MutedGray,
                                    modifier = Modifier.height(14.dp)
                                )
                                Text(
                                    text = "QR ${i + 1}",
                                    fontSize = 9.sp,
                                    color = MutedGray
                                )
                            }
                        }
                    }
                }
            }

            // Message when 5 are found
            if (allFound) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SafetyYellow.copy(alpha = 0.15f))
                        .border(1.dp, SafetyYellow, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "VOCÊ ENCONTROU TODAS AS PEÇAS.",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        color = SafetyYellow
                    )
                    Text(
                        text = "Agora falta montar a mensagem.",
                        fontSize = 12.sp,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = onNavigateToAssembly,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SafetyYellow,
                            contentColor = HazardBlack
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("montar_mensagem_button")
                    ) {
                        Text(
                            text = "MONTAR A FRASE OFICIAL",
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            } else {
                Text(
                    text = "Uma peça sozinha não completa o quebra-cabeça. Escaneie os 5 QR codes dos panfletos para revelar as 5 palavras.",
                    fontSize = 11.sp,
                    color = IndustrialGray,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
