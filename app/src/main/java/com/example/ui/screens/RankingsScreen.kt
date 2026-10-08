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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.example.data.local.ParticipantEntity
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
fun RankingsScreen(
    allParticipants: List<ParticipantEntity>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf<String>("TODOS") } // "TODOS", "MANHA", "TARDE", "NOITE"
    var searchQuery by remember { mutableStateOf("") }

    // Filter participants who completed the quiz
    val completedParticipants = remember(allParticipants) {
        allParticipants.filter { it.quizCompleted }
    }

    val filteredList = remember(completedParticipants, selectedTab, searchQuery) {
        val byPeriod = if (selectedTab == "TODOS") {
            completedParticipants
        } else {
            completedParticipants.filter { it.period == selectedTab }
        }

        if (searchQuery.isBlank()) {
            byPeriod
        } else {
            byPeriod.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.id.contains(searchQuery, ignoreCase = true) ||
                        it.classRoom.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val prizeCutoff = when (selectedTab) {
        "MANHA" -> 67
        "TARDE" -> 67
        "NOITE" -> 66
        else -> 200
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CharcoalBlack)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HazardStrip(heightDp = 8)

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("rankings_back_button")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = SafetyYellow
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "PAINEL OFICIAL DE SST",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialGray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "RANKING DA INVESTIGAÇÃO",
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
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${filteredList.size} PARTICIPANTES",
                        fontWeight = FontWeight.Black,
                        fontSize = 10.sp,
                        color = HazardBlack,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            // Period Tabs (Section 21: TODOS, MANHÃ, TARDE, NOITE)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "TODOS" to "GERAL",
                    "MANHA" to "☀️ MANHÃ (67)",
                    "TARDE" to "🌤️ TARDE (67)",
                    "NOITE" to "🌙 NOITE (66)"
                ).forEach { (key, label) ->
                    val isSelected = selectedTab == key
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) SafetyYellow else SurfaceCard)
                            .border(
                                1.dp,
                                if (isSelected) SafetyYellow else SurfaceCardBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { selectedTab = key }
                            .padding(vertical = 8.dp, horizontal = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isSelected) HazardBlack else TextWhite,
                            maxLines = 1
                        )
                    }
                }
            }

            // Search Filter
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar pelo nome ou ID...", color = IndustrialGray, fontSize = 12.sp) },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = SafetyYellow, modifier = Modifier.size(18.dp))
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SurfaceDark,
                    unfocusedContainerColor = SurfaceDark,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedLabelColor = SafetyYellow,
                    cursorColor = SafetyYellow,
                    focusedIndicatorColor = SafetyYellow,
                    unfocusedIndicatorColor = SurfaceCardBorder
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .height(50.dp)
                    .testTag("ranking_search_input")
            )

            // Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HazardBlack)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "POS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = IndustrialGray, modifier = Modifier.weight(0.18f))
                Text(text = "PARTICIPANTE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = IndustrialGray, modifier = Modifier.weight(0.44f))
                Text(text = "PONTOS", fontSize = 10.sp, fontWeight = FontWeight.Black, color = IndustrialGray, modifier = Modifier.weight(0.20f), fontFamily = FontFamily.Monospace)
                Text(text = "TEMPO", fontSize = 10.sp, fontWeight = FontWeight.Black, color = IndustrialGray, modifier = Modifier.weight(0.18f), fontFamily = FontFamily.Monospace)
            }

            // List of ranked participants
            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = IndustrialGray,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = if (searchQuery.isNotBlank()) "Nenhum participante encontrado para essa busca." else "Nenhum participante concluiu a investigação ainda neste período.",
                            color = TextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Conforme os alunos jogarem as duas etapas e completarem o desafio, suas pontuações aparecerão automaticamente aqui em tempo real!",
                            color = IndustrialGray,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    itemsIndexed(filteredList) { index, item ->
                    val rank = index + 1
                    val isInPrize = rank <= prizeCutoff

                    val minutes = item.timeSeconds / 60
                    val seconds = item.timeSeconds % 60
                    val formattedTime = String.format("%02d:%02d", minutes, seconds)

                    Column {
                        // Section 21: Prize Cutoff Divider Line
                        if (index == prizeCutoff) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SafetyAmber)
                                    .padding(vertical = 6.dp, horizontal = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EmojiEvents,
                                        contentDescription = null,
                                        tint = HazardBlack,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "FAIXA DE PREMIAÇÃO (ATÉ AQUI GANHA O COPO)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = HazardBlack,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }
                        }

                        // Participant Row
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isInPrize) SurfaceDark else HazardBlack)
                                .border(
                                    width = 1.dp,
                                    color = if (rank <= 3) SafetyYellow else if (isInPrize) SurfaceCardBorder else SurfaceCardBorder.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Rank badge
                                Row(
                                    modifier = Modifier.weight(0.18f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    if (rank <= 3) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = SafetyYellow,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                    Text(
                                        text = "${rank}º",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 12.sp,
                                        color = if (rank <= 3) SafetyYellow else TextWhite,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }

                                // Name & Period
                                Column(modifier = Modifier.weight(0.44f)) {
                                    Text(
                                        text = item.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = TextWhite,
                                        maxLines = 1
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = item.period,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SafetyYellow
                                        )
                                        Text(
                                            text = "• Turma ${item.classRoom}",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextWhite
                                        )
                                        Text(
                                            text = "• ${item.mistakes} erros",
                                            fontSize = 9.sp,
                                            color = IndustrialGray
                                        )
                                    }
                                }

                                // Points
                                Text(
                                    text = "${item.score}",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    color = SafetyYellow,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.weight(0.20f)
                                )

                                // Time
                                Text(
                                    text = formattedTime,
                                    fontSize = 11.sp,
                                    color = IndustrialGray,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.weight(0.18f)
                                )
                            }
                        }
                    }
                }
            }
        }

            HazardBanner(text = "JOGANDO JUNTOS, CONSTRUÍMOS NOSSA SEGURANÇA")
        }
    }
}
