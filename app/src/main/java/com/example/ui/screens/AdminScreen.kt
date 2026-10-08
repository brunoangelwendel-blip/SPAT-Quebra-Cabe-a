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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.CampaignSettingsEntity
import com.example.data.local.ParticipantEntity
import com.example.data.model.Period
import com.example.ui.components.CupCounterCard
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

@Composable
fun AdminScreen(
    settings: CampaignSettingsEntity,
    participants: List<ParticipantEntity>,
    redeemedMorning: Int,
    redeemedAfternoon: Int,
    redeemedNight: Int,
    totalRedeemed: Int,
    onToggleCompetition: (isClosed: Boolean) -> Unit,
    onRedeemCode: (String, (Boolean, ParticipantEntity?) -> Unit) -> Unit,
    onResetDatabase: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var adminPassword by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    // Validation form state
    var lookupCode by remember { mutableStateOf("") }
    var foundParticipant by remember { mutableStateOf<ParticipantEntity?>(null) }
    var redeemFeedback by remember { mutableStateOf<String?>(null) }
    var isFeedbackSuccess by remember { mutableStateOf(false) }

    // CSV dialog state
    var showCsvDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var resetConfirmationText by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // Passwords accepted: "m02sst", "admin123", "spat2026", "espat2026", "senac"
    fun attemptLogin() {
        if (adminPassword.trim().lowercase() in listOf("m02sst", "admin123", "spat2026", "espat2026", "senac")) {
            isAuthenticated = true
            loginError = false
        } else {
            loginError = true
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
                IconButton(onClick = onBack, modifier = Modifier.testTag("admin_back_button")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = SafetyYellow
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "GERENCIAMENTO RESTRITO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialGray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "PAINEL DO ADMINISTRADOR (/admin)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = TextWhite
                    )
                }

                if (isAuthenticated) {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(PrizeGreen)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "AUTORIZADO",
                            fontWeight = FontWeight.Black,
                            fontSize = 10.sp,
                            color = HazardBlack
                        )
                    }
                }
            }

            if (!isAuthenticated) {
                // Section 22: Authentication login
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(SafetyYellow)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(HazardBlack)
                                .border(1.dp, SafetyYellow, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = SafetyYellow,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Text(
                            text = "ACESSO RESTRITO À ORGANIZAÇÃO",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = SafetyYellow,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = "Área reservada aos organizadores da Turma M02 e coordenação pedagógica do Senac Vila Prudente.",
                            fontSize = 12.sp,
                            color = IndustrialGray,
                            textAlign = TextAlign.Center
                        )

                        OutlinedTextField(
                            value = adminPassword,
                            onValueChange = {
                                adminPassword = it
                                loginError = false
                            },
                            label = { Text("Senha de Administrador") },
                            placeholder = { Text("Dica: m02sst") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            singleLine = true,
                            isError = loginError,
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
                                .testTag("admin_password_input")
                        )

                        if (loginError) {
                            Text(
                                text = "Senha incorreta. Utilize a chave de acesso da comissão M02.",
                                color = DangerRed,
                                fontSize = 11.sp
                            )
                        }

                        Button(
                            onClick = { attemptLogin() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SafetyYellow,
                                contentColor = HazardBlack
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("admin_login_button")
                        ) {
                            Text(text = "ENTRAR NO PAINEL", fontWeight = FontWeight.Black, fontSize = 13.sp)
                        }
                    }
                }
            } else {
                // AUTHENTICATED ADMIN DASHBOARD
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Control Cards: Cup Counters
                    CupCounterCard(
                        redeemedMorning = redeemedMorning,
                        redeemedAfternoon = redeemedAfternoon,
                        redeemedNight = redeemedNight,
                        totalRedeemed = totalRedeemed
                    )

                    // SECTION 24: VALIDAÇÃO DOS CÓDIGOS DE RESGATE
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(SafetyYellow)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QrCode,
                                    contentDescription = null,
                                    tint = SafetyYellow
                                )
                                Text(
                                    text = "VALIDAÇÃO DE CÓDIGO DE RESGATE",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    color = SafetyYellow
                                )
                            }

                            Text(
                                text = "Digite o código apresentado pelo aluno premiado para validar e registrar a entrega do copo:",
                                fontSize = 11.sp,
                                color = IndustrialGray
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = lookupCode,
                                    onValueChange = {
                                        lookupCode = it.uppercase()
                                        redeemFeedback = null
                                    },
                                    placeholder = { Text("Ex: SPAT-7K42", color = IndustrialGray, fontSize = 12.sp) },
                                    singleLine = true,
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = HazardBlack,
                                        unfocusedContainerColor = HazardBlack,
                                        focusedTextColor = TextWhite,
                                        unfocusedTextColor = TextWhite,
                                        focusedLabelColor = SafetyYellow,
                                        cursorColor = SafetyYellow
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("lookup_code_input")
                                )

                                Button(
                                    onClick = {
                                        if (lookupCode.isNotBlank()) {
                                            onRedeemCode(lookupCode) { success, participant ->
                                                foundParticipant = participant
                                                if (participant == null) {
                                                    isFeedbackSuccess = false
                                                    redeemFeedback = "Código não encontrado no sistema."
                                                } else if (!success) {
                                                    isFeedbackSuccess = false
                                                    redeemFeedback = "Atenção: Este copo já foi retirado anteriormente!"
                                                } else {
                                                    isFeedbackSuccess = true
                                                    redeemFeedback = "Sucesso: Entrega do copo confirmada!"
                                                }
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SafetyYellow,
                                        contentColor = HazardBlack
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("verificar_codigo_button")
                                ) {
                                    Text("BUSCAR / ENTREGAR", fontWeight = FontWeight.Black, fontSize = 11.sp)
                                }
                            }

                            // Participant Details Result Box
                            if (foundParticipant != null) {
                                val p = foundParticipant!!
                                val isRetirado = p.redemptionStatus == "RETIRADO"

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(HazardBlack)
                                        .border(
                                            1.dp,
                                            if (isRetirado) PrizeGreen else SafetyAmber,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp)
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(
                                            text = "PARTICIPANTE: ${p.name.uppercase()}",
                                            fontWeight = FontWeight.Black,
                                            fontSize = 13.sp,
                                            color = TextWhite
                                        )
                                        Text(
                                            text = "PERÍODO: ${p.period} • PONTOS: ${p.score} • TEMPO: ${p.timeSeconds}s",
                                            fontSize = 11.sp,
                                            color = SafetyYellow,
                                            fontFamily = FontFamily.Monospace
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                text = "STATUS DO PRÊMIO:",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = IndustrialGray
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(if (isRetirado) PrizeGreen else SafetyAmber)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = if (isRetirado) "JÁ RETIRADO" else "PENDENTE DE RETIRADA",
                                                    fontWeight = FontWeight.Black,
                                                    fontSize = 10.sp,
                                                    color = HazardBlack
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            if (redeemFeedback != null) {
                                Text(
                                    text = redeemFeedback!!,
                                    color = if (isFeedbackSuccess) PrizeGreen else DangerRed,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // SECTION 25: ENCERRAMENTO DA COMPETIÇÃO
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = if (settings.isFrozen) Icons.Default.LockClock else Icons.Default.LockOpen,
                                    contentDescription = null,
                                    tint = if (settings.isFrozen) DangerRed else PrizeGreen
                                )
                                Text(
                                    text = "STATUS DA COMPETIÇÃO",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    color = TextWhite
                                )
                            }

                            Text(
                                text = if (settings.isFrozen) {
                                    "A competição está CONGELADA e ENCERRADA. Novas tentativas estão bloqueadas e a lista de 200 premiados foi fixada."
                                } else {
                                    "A competição está ABERTA. Participantes podem jogar e as posições continuam provisórias."
                                },
                                fontSize = 12.sp,
                                color = IndustrialGray,
                                lineHeight = 16.sp
                            )

                            Button(
                                onClick = { onToggleCompetition(!settings.isFrozen) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (settings.isFrozen) PrizeGreen else DangerRed,
                                    contentColor = ColorWhiteText()
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("toggle_competicao_button")
                            ) {
                                Text(
                                    text = if (settings.isFrozen) "REABRIR COMPETIÇÃO" else "ENCERRAR E CONGELAR RANKING",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    // Export Data (CSV) & Database Reset
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showCsvDialog = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = SafetyYellow),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SafetyYellow),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("export_csv_button")
                        ) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.size(6.dp))
                            Text("EXPORTAR CSV", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { showResetDialog = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = DangerRed),
                            border = androidx.compose.foundation.BorderStroke(1.dp, DangerRed),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("reset_db_button")
                        ) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.size(6.dp))
                            Text("RESETAR DADOS", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HazardBanner(text = "PAINEL RESTRITO • SENAC VILA PRUDENTE M02")
        }
    }

    // CSV Dialog
    if (showCsvDialog) {
        val csvContent = remember(participants) {
            val header = "ID,Nome,Periodo,Turma,Pontos,TempoSegundos,Erros,CodigoResgate,StatusResgate\n"
            val rows = participants.joinToString("\n") {
                "${it.id},\"${it.name}\",${it.period},${it.classRoom},${it.score},${it.timeSeconds},${it.mistakes},${it.redemptionCode ?: ""},${it.redemptionStatus}"
            }
            header + rows
        }

        AlertDialog(
            onDismissRequest = { showCsvDialog = false },
            title = {
                Text("Exportação de Dados (CSV)", color = SafetyYellow, fontWeight = FontWeight.Black)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Total de ${participants.size} registros exportados:",
                        color = TextWhite,
                        fontSize = 12.sp
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(HazardBlack)
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = csvContent,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = IndustrialGray
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCsvDialog = false }) {
                    Text("FECHAR", color = SafetyYellow, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = SurfaceDark
        )
    }

    // Double-confirmation Reset Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = {
                showResetDialog = false
                resetConfirmationText = ""
            },
            title = {
                Text("CONFIRMAÇÃO DUPLA DE RESET", color = DangerRed, fontWeight = FontWeight.Black)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Esta ação apagará permanentemente todos os participantes, tempos, pontos e códigos de resgate da SPAT.",
                        color = TextWhite,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Para confirmar, digite ZERAR abaixo:",
                        color = SafetyYellow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = resetConfirmationText,
                        onValueChange = { resetConfirmationText = it },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = HazardBlack,
                            unfocusedContainerColor = HazardBlack,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (resetConfirmationText.trim().uppercase() == "ZERAR") {
                            onResetDatabase()
                            showResetDialog = false
                            resetConfirmationText = ""
                        }
                    },
                    enabled = resetConfirmationText.trim().uppercase() == "ZERAR",
                    colors = ButtonDefaults.buttonColors(containerColor = DangerRed)
                ) {
                    Text("ZERAR BANCO DE DADOS", fontWeight = FontWeight.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showResetDialog = false
                    resetConfirmationText = ""
                }) {
                    Text("CANCELAR", color = IndustrialGray)
                }
            },
            containerColor = SurfaceDark
        )
    }
}

@Composable
private fun ColorWhiteText() = TextWhite
