package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HazardBlack
import com.example.ui.theme.SafetyYellow

@Composable
fun HazardStrip(
    modifier: Modifier = Modifier,
    heightDp: Int = 14
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(heightDp.dp)
    ) {
        val width = size.width
        val height = size.height
        val stripeWidth = height * 1.5f

        // Fill background yellow
        drawRect(color = SafetyYellow)

        // Draw diagonal black stripes
        var x = -height
        while (x < width + height) {
            drawLine(
                color = HazardBlack,
                start = Offset(x, 0f),
                end = Offset(x + height, height),
                strokeWidth = stripeWidth * 0.6f
            )
            x += stripeWidth
        }
    }
}

@Composable
fun HazardBanner(
    text: String = "/// ÁREA DE INVESTIGAÇÃO PERICIAL - SST SENAC M02 ///",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SafetyYellow)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            color = HazardBlack,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp
        )
    }
}
