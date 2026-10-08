package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SafetyYellow,
    onPrimary = HazardBlack,
    primaryContainer = SafetyYellowDark,
    onPrimaryContainer = HazardBlack,
    secondary = SafetyAmber,
    onSecondary = HazardBlack,
    background = CharcoalBlack,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = IndustrialGray,
    outline = SurfaceCardBorder,
    error = DangerRed,
    onError = Color.White
)

private val LightColorScheme = darkColorScheme(
    // Industrial safety app is consistently dark noir high-contrast
    primary = SafetyYellow,
    onPrimary = HazardBlack,
    primaryContainer = SafetyYellowDark,
    onPrimaryContainer = HazardBlack,
    secondary = SafetyAmber,
    onSecondary = HazardBlack,
    background = CharcoalBlack,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = IndustrialGray,
    outline = SurfaceCardBorder,
    error = DangerRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false, // Keep high contrast brand colors
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}

