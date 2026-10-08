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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.model.EvidenceData
import com.example.ui.components.HazardBanner
import com.example.ui.components.HazardStrip
import com.example.ui.theme.CharcoalBlack
import com.example.ui.theme.HazardBlack
import com.example.ui.theme.IndustrialGray
import com.example.ui.theme.SafetyYellow
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextWhite

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

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
                IconButton(onClick = onBack, modifier = Modifier.testTag("about_back_button")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = SafetyYellow
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "IDENTIDADE INSTITUCIONAL",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialGray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "SOBRE A CAMPANHA SPAT",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = TextWhite
                    )
                }
            }

            // Institutional Info Card (Section 1)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SafetyYellow)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = SafetyYellow,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "SENAC VILA PRUDENTE",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = SafetyYellow
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "CURSO:", fontSize = 10.sp, color = IndustrialGray, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Técnico em Segurança do Trabalho",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "TURMA:", fontSize = 10.sp, color = IndustrialGray, fontWeight = FontWeight.Bold)
                            Text(
                                text = "M02",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = SafetyYellow,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Column(modifier = Modifier.weight(1.5f)) {
                            Text(text = "PROFESSOR ORIENTADOR:", fontSize = 10.sp, color = IndustrialGray, fontWeight = FontWeight.Bold)
                            Text(
                                text = "Daniel Juviano",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = SafetyYellow
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Professor Orientador Destaque Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SafetyYellow)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(HazardBlack)
                            .border(1.dp, SafetyYellow, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = null,
                            tint = SafetyYellow,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            text = "PROFESSOR ORIENTADOR",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = SafetyYellow,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Daniel Juviano",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = TextWhite
                        )
                        Text(
                            text = "Docente Orientador • Senac Vila Prudente",
                            fontSize = 11.sp,
                            color = IndustrialGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Criadores do Jogo Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SurfaceCardBorder)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Groups,
                            contentDescription = null,
                            tint = SafetyYellow,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = "CRIADORES DO JOGO",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = SafetyYellow
                        )
                    }

                    Text(
                        text = "Estudantes e idealizadores da Turma M02 de Técnico em Segurança do Trabalho:",
                        fontSize = 11.sp,
                        color = IndustrialGray
                    )

                    val creators = listOf(
                        "Anderson Gonçalves" to "Criador / Desenvolvedor do Projeto",
                        "Bruno Wendel" to "Criador / Idealizador do Jogo",
                        "Claudia Cristina" to "Criadora / Conteúdo e Pesquisa",
                        "Claudia Pires" to "Criadora / Investigação e Pistas",
                        "Erick Ribeiro" to "Criador / Dinâmica e Regras"
                    )

                    creators.forEach { (name, role) ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(HazardBlack)
                                .border(1.dp, SurfaceCardBorder, RoundedCornerShape(8.dp))
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = SafetyYellow,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Column {
                                        Text(
                                            text = name,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 13.sp,
                                            color = TextWhite
                                        )
                                        Text(
                                            text = role,
                                            fontSize = 10.sp,
                                            color = IndustrialGray
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(SurfaceCard)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "TURMA M02",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp,
                                        color = SafetyYellow,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Campaign Concept Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SurfaceCardBorder)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "CONCEITO CENTRAL DA INVESTIGAÇÃO",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        color = SafetyYellow
                    )
                    Text(
                        text = "A SPAT Quebra-Cabeça transforma a tradicional Semana de Prevenção em uma experiência de investigação realista. Um acidente simulado no Senac Vila Prudente gera cinco pistas periciais. Cada peça revela uma palavra da frase mestra que resume o espírito de SST.",
                        fontSize = 12.sp,
                        color = TextWhite,
                        lineHeight = 17.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(HazardBlack)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "\"${EvidenceData.OFFICIAL_FULL_PHRASE}\"",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = SafetyYellow,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HazardBanner(text = "JOGANDO JUNTOS, CONSTRUÍMOS NOSSA SEGURANÇA")
        }
    }
}
