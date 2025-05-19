package com.updevelop.calculadoraapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.updevelop.calculadoraapp.ui.screens.SplashScreen
import com.updevelop.calculadoraapp.ui.theme.CalculadoraAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            CalculadoraAppTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen {
                            navController.navigate("main") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }
                    composable("main") {
                        CalculadoraApp(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { isDarkTheme = !isDarkTheme }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculadoraApp(isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    val historial = remember { mutableStateListOf<String>() }

    val colorFondo = MaterialTheme.colorScheme.background
    val colorTitulo = if (isDarkTheme) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary
    val colorTexto = if (isDarkTheme) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🌙", modifier = Modifier.padding(end = 8.dp))
            Switch(checked = isDarkTheme, onCheckedChange = { onToggleTheme() })
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Calculadora Up Develop",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = colorTitulo
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = numero1,
                onValueChange = { numero1 = it },
                label = { Text("Número 1") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            TextField(
                value = numero2,
                onValueChange = { numero2 = it },
                label = { Text("Número 2") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf("+", "-", "×", "÷").forEach { op ->
                    Button(
                        onClick = {
                            val n1 = numero1.toFloatOrNull()
                            val n2 = numero2.toFloatOrNull()

                            resultado = if (n1 != null && n2 != null) {
                                val r = when (op) {
                                    "+" -> n1 + n2
                                    "-" -> n1 - n2
                                    "×" -> n1 * n2
                                    "÷" -> if (n2 != 0f) n1 / n2 else null
                                    else -> null
                                }

                                if (r != null) {
                                    val rStr = r.toString()
                                    historial.add("$n1 $op $n2 = $rStr")
                                    rStr
                                } else {
                                    "Error"
                                }
                            } else {
                                "Números inválidos"
                            }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .width(70.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(op)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        numero1 = ""
                        numero2 = ""
                        resultado = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Reiniciar")
                }
                Button(
                    onClick = {
                        numero1 = ""
                        numero2 = ""
                        resultado = ""
                        historial.clear()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Borrar Todo")
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Resultado:",
                fontSize = 20.sp,
                color = colorTexto
            )
            Text(
                text = resultado,
                fontSize = 24.sp,
                color = colorTexto
            )

            if (historial.isNotEmpty()) {
                Text("Historial:", fontSize = 18.sp, color = colorTexto)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                ) {
                    items(historial.reversed()) { item ->
                        Text(text = item, fontSize = 14.sp, color = colorTexto)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(name = "Calculadora - Modo Claro", showBackground = true)
@Composable
fun CalculadoraPreviewLight() {
    CalculadoraAppTheme(darkTheme = false) {
        CalculadoraApp(
            isDarkTheme = false,
            onToggleTheme = {}
        )
    }
}

@Preview(name = "Calculadora - Modo Oscuro", showBackground = true)
@Composable
fun CalculadoraPreviewDark() {
    CalculadoraAppTheme(darkTheme = true) {
        CalculadoraApp(
            isDarkTheme = true,
            onToggleTheme = {}
        )
    }
}