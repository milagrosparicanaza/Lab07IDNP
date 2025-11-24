package com.example.listapacientesanemia.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController   // <-- IMPORT NECESARIO

@Composable
fun ControlAnemiaScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                navController.navigate("registroResultado")   // <-- Ruta correcta
            }
        ) {
            Text("Registrar nuevo resultado")
        }
    }
}


