package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.R
import com.example.data.local.ParticipantEntity
import com.example.ui.components.CupCounterCard
import com.example.ui.components.HazardBanner
import com.example.ui.components.HazardStrip
import com.example.ui.components.PuzzleProgressCard
import com.example.ui.theme.CharcoalBlack
import com.example.ui.theme.HazardBlack
import com.example.ui.theme.IndustrialGray
import com.example.ui.theme.SafetyYellow
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextWhite

@Composable
fun HomeScreen(
    currentParticipant: ParticipantEntity?,
    redeemedMorning: Int,
    redeemedAfternoon: Int,
    redeemedNight: Int,
    totalRedeemed: Int,
    onStartInvestigation: () -> Unit,
    onOpenScanner: () -> Unit,
    onSelectEvidence: (Int) -> Unit,
    onNavigateToAssembly: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToRankings: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val evidencesFound = currentParticipant?.getEvidenceList() ?: listOf(false, false, false, false, false)

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
            // Hazard Strip Header
            HazardStrip(heightDp = 10)

            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(SafetyYellow),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "M02",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            color = HazardBlack
                        )
                    }
                    Column {
                        Text(
                            text = "SENAC VILA PRUDENTE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialGray,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "SEGURANÇA DO TRABALHO",
                            fontSize = 9.sp,
                            color = SafetyYellow
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = onNavigateToAbout,
                        modifier = Modifier.testTag("about_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Sobre a Campanha",
                            tint = IndustrialGray
                        )
                    }
                    IconButton(
                        onClick = onNavigateToAdmin,
                        modifier = Modifier.testTag("admin_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Painel Admin",
                            tint = SafetyYellow
                        )
                    }
                }
            }

            // Hero Banner & Cinematic Intro
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SurfaceCardBorder)
                )
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hero_espat_banner_1789603603119),
                            contentDescription = "Banner SPAT Quebra-Cabeça",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    androidx.compose.ui.graphics.Brush.verticalGradient(
                                        colors = listOf(
                                            androidx.compose.ui.graphics.Color.Transparent,
                                            SurfaceDark
                                        ),
                                        startY = 60f
                                    )
                                )
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(HazardBlack.copy(alpha = 0.85f))
                                .border(1.dp, SafetyYellow, RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "200 COPOS EXCLUSIVOS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = SafetyYellow,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "SPAT",
                            fontWeight = FontWeight.Black,
                            fontSize = 32.sp,
                            color = SafetyYellow,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "QUEBRA-CABEÇA",
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            color = TextWhite,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "INVESTIGANDO PARA UM AMBIENTE MAIS SEGURO",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SafetyYellow,
                            letterSpacing = 0.5.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Um acidente aconteceu no Senac Vila Prudente.\nCinco evidências foram encontradas.\nCada evidência guarda uma peça.\nVocê consegue encontrar todas?",
                            fontSize = 13.sp,
                            color = IndustrialGray,
                            lineHeight = 18.sp
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceCard)
                                .border(1.dp, SurfaceCardBorder, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "\"Mas existe uma regra: Uma peça sozinha não completa o quebra-cabeça.\"",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SafetyYellow,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onStartInvestigation,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SafetyYellow,
                        contentColor = HazardBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("iniciar_investigacao_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "INICIAR INVESTIGAÇÃO",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onOpenScanner,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = SafetyYellow),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SafetyYellow),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("escanear_qr_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "LER QR CODE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onNavigateToRankings,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceCardBorder),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("rankings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = SafetyYellow,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "RANKING",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Puzzle Progress Card
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                PuzzleProgressCard(
                    evidencesFound = evidencesFound,
                    onSelectEvidence = onSelectEvidence,
                    onNavigateToAssembly = onNavigateToAssembly
                )
            }

            // If phrase is completed, offer shortcut to Quiz
            if (currentParticipant?.phraseCompleted == true && currentParticipant.quizCompleted != true) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Button(
                        onClick = onNavigateToQuiz,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SafetyYellow,
                            contentColor = HazardBlack
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ir_desafio_button")
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "CONTINUAR DESAFIO DO INVESTIGADOR",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cup Counter Card (200 Copos)
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                CupCounterCard(
                    redeemedMorning = redeemedMorning,
                    redeemedAfternoon = redeemedAfternoon,
                    redeemedNight = redeemedNight,
                    totalRedeemed = totalRedeemed
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Official Campaign Phrase Banner
            HazardBanner(text = "JOGANDO JUNTOS, CONSTRUÍMOS NOSSA SEGURANÇA")
        }
    }
}
