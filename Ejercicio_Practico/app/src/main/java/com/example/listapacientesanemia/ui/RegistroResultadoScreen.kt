package com.example.listapacientesanemia.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.listapacientesanemia.model.AnemiaResult
import com.example.listapacientesanemia.model.AnemiaRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegistroResultadoScreen(navController: NavController, repo: AnemiaRepository) {

    var nombre by remember { mutableStateOf("") }
    var hemoglobina by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(20.dp)) {

        Text("Registrar resultado", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Paciente") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = hemoglobina,
            onValueChange = { hemoglobina = it },
            label = { Text("Hemoglobina (g/dL)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val valor = hemoglobina.toDoubleOrNull() ?: 0.0
                val resultado = if (valor < 12) "Anemia" else "Normal"

                val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                scope.launch {
                    repo.guardarResultado(
                        AnemiaResult(
                            paciente = nombre,
                            hemoglobina = valor,
                            resultado = resultado,
                            fecha = fecha
                        )
                    )
                    navController.navigate("listaResultados")
                }
            }
        ) {
            Text("Guardar Resultado")
        }
    }
}
