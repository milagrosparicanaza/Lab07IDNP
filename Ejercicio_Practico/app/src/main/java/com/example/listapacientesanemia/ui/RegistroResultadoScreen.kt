package com.example.listapacientesanemia.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RegistroResultadoScreen(
    navController: NavController,
    viewModel: AnemiaViewModel
) {

    var nombrePaciente by remember { mutableStateOf("") }
    var hemoglobinaInput by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    // Generar fecha actual
    val fechaActual = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }

    fun calcularResultado(hb: Float): String {
        return when {
            hb >= 12 -> "Normal"
            hb >= 10 -> "Anemia Leve"
            hb >= 8 -> "Anemia Moderada"
            else -> "Anemia Severa"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Registrar Resultado de Anemia",
            style = MaterialTheme.typography.headlineSmall
        )

        // NOMBRE
        OutlinedTextField(
            value = nombrePaciente,
            onValueChange = { nombrePaciente = it },
            label = { Text("Nombre del paciente") },
            modifier = Modifier.fillMaxWidth()
        )

        // HEMOGLOBINA
        OutlinedTextField(
            value = hemoglobinaInput,
            onValueChange = { hemoglobinaInput = it },
            label = { Text("Hemoglobina (g/dL)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // BOTÓN CALCULAR
        Button(
            onClick = {
                if (hemoglobinaInput.isNotBlank()) {
                    val hb = hemoglobinaInput.toFloat()
                    resultado = calcularResultado(hb)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular resultado")
        }

        // Mostrar resultado
        if (resultado.isNotEmpty()) {
            Text(
                text = "Resultado: $resultado",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Fecha: $fechaActual",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BOTÓN GUARDAR
        Button(
            onClick = {
                if (
                    nombrePaciente.isNotBlank() &&
                    hemoglobinaInput.isNotBlank() &&
                    resultado.isNotBlank()
                ) {
                    viewModel.guardarResultado(
                        paciente = nombrePaciente,
                        nivelHemoglobina = hemoglobinaInput.toFloat(),
                        resultado = resultado,
                        fecha = fechaActual
                    )
                    navController.navigate("listaResultados")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar resultado")
        }
    }
}
