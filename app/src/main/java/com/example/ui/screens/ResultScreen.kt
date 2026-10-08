package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ParticipantEntity
import com.example.data.model.EvidenceData
import com.example.data.model.Period
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
fun ResultScreen(
    participant: ParticipantEntity,
    currentRank: Int,
    periodLimit: Int,
    onNavigateToHome: () -> Unit,
    onNavigateToRankings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val period = Period.fromString(participant.period)
    val isInPrizeZone = currentRank in 1..periodLimit

    val minutes = participant.timeSeconds / 60
    val seconds = participant.timeSeconds % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

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

            // Header Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SafetyYellow),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = HazardBlack,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "INVESTIGAÇÃO CONCLUÍDA",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = SafetyYellow,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Parabéns, ${participant.name}!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )

                    Text(
                        text = "ID: ${participant.id} • TURMA: ${participant.classRoom} • PERÍODO: ${period.displayName}",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = IndustrialGray
                    )
                }
            }

            // Summary Stats Cards
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "PONTUAÇÃO",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialGray
                        )
                        Text(
                            text = "${participant.score}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = SafetyYellow,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(text = "pontos", fontSize = 10.sp, color = IndustrialGray)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "TEMPO TOTAL",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialGray
                        )
                        Text(
                            text = formattedTime,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = TextWhite,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(text = "minutos", fontSize = 10.sp, color = IndustrialGray)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "ERROS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialGray
                        )
                        Text(
                            text = "${participant.mistakes}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = if (participant.mistakes == 0) PrizeGreen else SafetyAmber,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(text = "questões", fontSize = 10.sp, color = IndustrialGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Ranking Position & Prize Status (Section 15, 18)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("prize_status_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        if (isInPrizeZone) PrizeGreen else SafetyAmber
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = if (isInPrizeZone) PrizeGreen else SafetyAmber
                            )
                            Text(
                                text = "POSIÇÃO PROVISÓRIA",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = TextWhite
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isInPrizeZone) PrizeGreen else SurfaceCard)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${currentRank}º LUGAR (${period.displayName})",
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                color = if (isInPrizeZone) HazardBlack else SafetyYellow
                            )
                        }
                    }

                    if (isInPrizeZone) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrizeGreen.copy(alpha = 0.15f))
                                .border(1.dp, PrizeGreen, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "🎯 VOCÊ ESTÁ NA FAIXA DE PREMIAÇÃO DO COPO!",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    color = PrizeGreen
                                )
                                Text(
                                    text = "Sua posição (${currentRank}º) está dentro da cota de ${periodLimit} copos exclusivos para o período da ${period.displayName}.",
                                    fontSize = 11.sp,
                                    color = TextWhite,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SafetyAmber.copy(alpha = 0.15f))
                                .border(1.dp, SafetyAmber, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Você está na posição ${currentRank}º. A faixa de premiação do seu período contempla até o ${periodLimit}º colocado.",
                                fontSize = 11.sp,
                                color = TextWhite,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    // Mandatory Section 18 Warning
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(HazardBlack)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = SafetyYellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Aviso importante: Sua posição pode mudar até o encerramento do ranking. O resultado final será divulgado após o término da SPAT.",
                            fontSize = 10.sp,
                            color = IndustrialGray,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            // REDEMPTION CODE (Mandatory Section 19)
            if (participant.redemptionCode != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .testTag("redemption_code_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = HazardBlack),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(SafetyYellow)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "CÓDIGO DE RESGATE PROVISÓRIO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialGray,
                            letterSpacing = 1.sp
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SafetyYellow)
                                .padding(horizontal = 20.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = participant.redemptionCode,
                                fontWeight = FontWeight.Black,
                                fontSize = 22.sp,
                                color = HazardBlack,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = 2.sp
                            )
                        }

                        Text(
                            text = "Guarde este código. Ele poderá ser solicitado para a retirada do copo caso sua posição se mantenha até o fechamento da SPAT.",
                            fontSize = 11.sp,
                            color = TextWhite,
                            textAlign = TextAlign.Center,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // In-Person Pick-up Instructions (Section 20)
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = SafetyYellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            text = "RETIRADA PRESENCIAL DO PRÊMIO",
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp,
                            color = SafetyYellow
                        )
                        Text(
                            text = "Local: Senac Vila Prudente\nCriadores do Jogo: Anderson Gonçalves, Bruno Wendel, Claudia Cristina, Claudia Pires e Erick Ribeiro (Turma M02).\nProfessor Orientador: Daniel Juviano.",
                            fontSize = 11.sp,
                            color = TextWhite,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // OFFICIAL PHRASE HIGHLIGHT (Mandatory Section 15)
            Spacer(modifier = Modifier.height(14.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SafetyYellow)
                )
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "LEMA OFICIAL DA CAMPANHA SPAT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialGray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = EvidenceData.OFFICIAL_FULL_PHRASE,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = SafetyYellow,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "A segurança é uma conquista coletiva: cada trabalhador e aluno faz parte da engrenagem que protege vidas.",
                        fontSize = 11.sp,
                        color = TextWhite,
                        textAlign = TextAlign.Center,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onNavigateToRankings,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SafetyYellow,
                        contentColor = HazardBlack
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("ver_classificacao_button")
                ) {
                    Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "VER CLASSIFICAÇÃO GERAL",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                }

                OutlinedButton(
                    onClick = onNavigateToHome,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceCardBorder),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("voltar_inicio_button")
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "VOLTAR AO INÍCIO", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HazardBanner(text = "JOGANDO JUNTOS, CONSTRUÍMOS NOSSA SEGURANÇA")
        }
    }
}
