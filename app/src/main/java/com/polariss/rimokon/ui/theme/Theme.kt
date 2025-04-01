package com.polariss.rimokon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5),
    onSurface = Color(0xFF000000),
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF018786),
    error = Color(0xFFCF6679),
)

@Composable
fun RimokonTheme(
    content: @Composable () -> Unit
) {
MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}