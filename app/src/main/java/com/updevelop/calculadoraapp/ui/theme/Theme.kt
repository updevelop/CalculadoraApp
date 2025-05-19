package com.updevelop.calculadoraapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = AzulUpPrimario,
    onPrimary = Color.White,
    secondary = AzulSecundario,
    background = FondoClaro,
    surface = Color.White,
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = AzulSecundario,
    onPrimary = Color.Black,
    secondary = AzulUpPrimario,
    background = FondoOscuro,
    surface = Color.DarkGray,
    onSurface = Color.White,
)

@Composable
fun CalculadoraAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}