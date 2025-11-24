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
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModel
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModelFactory

@Composable
fun ListaResultadosScreen(
    navController: NavController,
    viewModel: AnemiaViewModel
) {

    val resultados by viewModel.resultados.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(resultados) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Hemoglobina: ${item.hemoglobina} g/dL")
                    Text("Fecha: ${item.fecha}")
                }
            }
        }
    }
}

