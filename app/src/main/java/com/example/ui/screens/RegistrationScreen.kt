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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Period
import com.example.ui.components.HazardBanner
import com.example.ui.components.HazardStrip
import com.example.ui.theme.CharcoalBlack
import com.example.ui.theme.DangerRed
import com.example.ui.theme.HazardBlack
import com.example.ui.theme.IndustrialGray
import com.example.ui.theme.SafetyYellow
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextWhite

@Composable
fun RegistrationScreen(
    initialName: String = "",
    initialPeriod: Period = Period.MANHA,
    initialClassRoom: String = "M02",
    onRegister: (name: String, period: Period, classRoom: String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(initialName) }
    var classRoom by remember { mutableStateOf(initialClassRoom) }
    var selectedPeriod by remember { mutableStateOf(initialPeriod) }
    var nameError by remember { mutableStateOf(false) }
    var classRoomError by remember { mutableStateOf(false) }

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
                IconButton(onClick = onBack, modifier = Modifier.testTag("reg_back_button")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = SafetyYellow
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CREDENCIAMENTO DE SST",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialGray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "CADASTRO DO INVESTIGADOR",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = TextWhite
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(SurfaceCardBorder)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = null,
                            tint = SafetyYellow,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "IDENTIFICAÇÃO DO PARTICIPANTE",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = SafetyYellow
                        )
                    }

                    Text(
                        text = "Identifique-se para registrar seu tempo e pontuação no ranking oficial de SST da Turma M02.",
                        fontSize = 12.sp,
                        color = IndustrialGray
                    )

                    // Nome input
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "NOME COMPLETO OU COMO PREFERE SER CHAMADO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                                nameError = false
                            },
                            placeholder = { Text("Ex: Juliana Silva", color = IndustrialGray) },
                            singleLine = true,
                            isError = nameError,
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = SafetyYellow)
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = HazardBlack,
                                unfocusedContainerColor = HazardBlack,
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
                                .testTag("nome_input")
                        )
                        if (nameError) {
                            Text(
                                text = "Por favor, informe seu nome.",
                                color = DangerRed,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Período selector (MANHÃ, TARDE, NOITE)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "PERÍODO LETIVO (DETERMINA O SEU RANKING)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Period.entries.forEach { p ->
                                val isSelected = selectedPeriod == p
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
                                        .clickable { selectedPeriod = p }
                                        .padding(vertical = 12.dp, horizontal = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = p.icon,
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = p.displayName,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 11.sp,
                                            color = if (isSelected) HazardBlack else TextWhite
                                        )
                                        Text(
                                            text = "${p.prizeLimit} copos",
                                            fontSize = 9.sp,
                                            color = if (isSelected) HazardBlack.copy(alpha = 0.8f) else IndustrialGray
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Turma input (Editable as requested: "escrever o nome dele é a turma que pertence")
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "TURMA A QUE PERTENCE (SENAC VILA PRUDENTE)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        OutlinedTextField(
                            value = classRoom,
                            onValueChange = {
                                classRoom = it
                                classRoomError = false
                            },
                            placeholder = { Text("Ex: M02, T01, N01, Técnico SST...", color = IndustrialGray) },
                            singleLine = true,
                            isError = classRoomError,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = HazardBlack,
                                unfocusedContainerColor = HazardBlack,
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
                                .testTag("turma_input")
                        )
                        if (classRoomError) {
                            Text(
                                text = "Por favor, informe a qual turma você pertence.",
                                color = DangerRed,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Privacy notice (Section 10 mandate: não solicitar CPF, telefone, endereço)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceCard)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "🔒 Privacidade: Não solicitamos CPF, telefone ou dados sensíveis. O sistema gera automaticamente um identificador único de perito.",
                            fontSize = 10.sp,
                            color = IndustrialGray,
                            lineHeight = 14.sp
                        )
                    }

                    Button(
                        onClick = {
                            var hasError = false
                            if (name.isBlank()) {
                                nameError = true
                                hasError = true
                            }
                            if (classRoom.isBlank()) {
                                classRoomError = true
                                hasError = true
                            }
                            if (!hasError) {
                                onRegister(name.trim(), selectedPeriod, classRoom.trim())
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SafetyYellow,
                            contentColor = HazardBlack
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("iniciar_desafio_button")
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "ENTRAR NO DESAFIO DO INVESTIGADOR",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HazardBanner(text = "JOGANDO JUNTOS, CONSTRUÍMOS NOSSA SEGURANÇA")
        }
    }
}
