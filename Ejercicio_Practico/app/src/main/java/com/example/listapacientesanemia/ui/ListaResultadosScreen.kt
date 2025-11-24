package com.example.listapacientesanemia.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.listapacientesanemia.model.AnemiaRepository

@Composable
fun ListaResultadosScreen(navController: NavController, repo: AnemiaRepository) {

    val resultados by repo.resultados.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(20.dp)) {
        Text("Resultados de Anemia", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(resultados) { r ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Paciente: ${r.paciente}")
                        Text("Hemoglobina: ${r.hemoglobina}")
                        Text("Diagnóstico: ${r.resultado}")
                        Text("Fecha: ${r.fecha}")
                    }
                }
            }
        }
    }
}
