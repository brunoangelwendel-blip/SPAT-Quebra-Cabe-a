package com.example.ui.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCodeScanner
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
import androidx.compose.ui.window.Dialog
import com.example.data.model.EvidenceData
import com.example.ui.theme.HazardBlack
import com.example.ui.theme.IndustrialGray
import com.example.ui.theme.SafetyYellow
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardBorder
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextWhite

@Composable
fun QRCodeScannerDialog(
    onDismiss: () -> Unit,
    onEvidenceScanned: (Int) -> Unit
) {
    var manualInput by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("qr_scanner_dialog"),
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = "Scanner",
                            tint = SafetyYellow,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "LEITOR DE QR CODE",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = TextWhite,
                            letterSpacing = 1.sp
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = IndustrialGray
                        )
                    }
                }

                Text(
                    text = "Aponte a câmera para o QR Code do panfleto físico ou selecione diretamente uma das 5 evidências encontradas no Senac:",
                    fontSize = 12.sp,
                    color = IndustrialGray,
                    lineHeight = 16.sp
                )

                // Quick buttons for QR 1..5
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    EvidenceData.evidences.forEach { ev ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceCard)
                                .border(1.dp, SurfaceCardBorder, RoundedCornerShape(8.dp))
                                .clickable {
                                    onEvidenceScanned(ev.id)
                                    onDismiss()
                                }
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "QR 0${ev.id} • ${ev.subtitle}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = TextWhite
                                    )
                                    Text(
                                        text = "/?evidencia=${ev.id}",
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = SafetyYellow
                                    )
                                }

                                Text(
                                    text = "ACESSAR →",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = SafetyYellow
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Manual URL or Code test
                OutlinedTextField(
                    value = manualInput,
                    onValueChange = {
                        manualInput = it
                        inputError = false
                        // Check if ends with evidencia=X
                        val match = Regex("""(?:evidencia=|ev=)(\d)""").find(it)
                        if (match != null) {
                            val id = match.groupValues[1].toIntOrNull()
                            if (id in 1..5) {
                                onEvidenceScanned(id!!)
                                onDismiss()
                            }
                        }
                    },
                    label = { Text("Digitar URL ou código (ex: /?evidencia=1)", fontSize = 11.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = HazardBlack,
                        unfocusedContainerColor = HazardBlack,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedLabelColor = SafetyYellow,
                        cursorColor = SafetyYellow
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
