package com.updevelop.calculadoraapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.updevelop.calculadoraapp.R
import com.updevelop.calculadoraapp.ui.theme.CalculadoraAppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(darkTheme: Boolean = isSystemInDarkTheme(),onTimeout: () -> Unit) {
    val fondo = if (darkTheme) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
    var scale by remember { mutableFloatStateOf(0f) }

    // Animación compuesta
    val animation = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(true) {
        delay(1500) // Espera total antes de navegar
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo Up Develop",
            modifier = Modifier
                .size(160.dp)
                .graphicsLayer(
                    scaleX = animation.value,
                    scaleY = animation.value
                )
        )
    }
}

@Preview(name = "Splash - Modo Claro", showBackground = true)
@Composable
fun SplashPreviewLight() {
    CalculadoraAppTheme(darkTheme = false) {
        SplashScreen(darkTheme = false, onTimeout = {})
    }
}

@Preview(name = "Splash - Modo Oscuro", showBackground = true)
@Composable
fun SplashPreviewDark() {
    CalculadoraAppTheme(darkTheme = true) {
        SplashScreen(darkTheme = true, onTimeout = {})
    }
}