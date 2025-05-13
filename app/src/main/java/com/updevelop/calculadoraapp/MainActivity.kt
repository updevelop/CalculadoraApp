package com.updevelop.calculadoraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.updevelop.calculadoraapp.ui.theme.CalculadoraAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraAppTheme {
                var numero1 by remember { mutableStateOf("") }
                var numero2 by remember { mutableStateOf("") }
                var resultado by remember { mutableStateOf("") }

                CalculadoraUI(
                    numero1 = numero1,
                    numero2 = numero2,
                    resultado = resultado,
                    onNumero1Change = { numero1 = it },
                    onNumero2Change = { numero2 = it },
                    onOperacion = { operacion ->
                        val n1 = numero1.toFloatOrNull()
                        val n2 = numero2.toFloatOrNull()

                        resultado = if (n1 != null && n2 != null) {
                            when (operacion) {
                                "+" -> (n1 + n2).toString()
                                "-" -> (n1 - n2).toString()
                                "×" -> (n1 * n2).toString()
                                "÷" -> if (n2 != 0f) (n1 / n2).toString() else "Error: división por 0"
                                else -> "Operación inválida"
                            }
                        } else {
                            "Números inválidos"
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CalculadoraUI(
    numero1: String,
    numero2: String,
    resultado: String,
    onNumero1Change: (String) -> Unit,
    onNumero2Change: (String) -> Unit,
    onOperacion: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = numero1,
            onValueChange = onNumero1Change,
            label = { Text("Número 1") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = numero2,
            onValueChange = onNumero2Change,
            label = { Text("Número 2") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("+", "-", "×", "÷").forEach { op ->
                Button(
                    onClick = { onOperacion(op) },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(70.dp)
                ) {
                    Text(op)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Resultado: $resultado", fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    CalculadoraAppTheme {
        CalculadoraUI(
            numero1 = "",
            numero2 = "",
            resultado = "0",
            onNumero1Change = {},
            onNumero2Change = {},
            onOperacion = {}
        )
    }
}