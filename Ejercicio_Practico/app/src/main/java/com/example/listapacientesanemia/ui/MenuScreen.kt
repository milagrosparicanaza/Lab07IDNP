package com.example.listapacientesanemia.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // FILA SUPERIOR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuButton(
                text = "Lista de Pacientes",
                onClick = { navController.navigate("home") }
            )
            MenuButton(
                text = "Control de Anemia",
                onClick = { navController.navigate("control_anemia") }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // FILA INFERIOR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuButton(
                text = "Prueba de Anemia",
                onClick = { navController.navigate("prueba_anemia") }
            )
            MenuButton(
                text = "Prevención de Anemia",
                onClick = { navController.navigate("prevencion") }
            )
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(150.dp)
            .height(120.dp)
    ) {
        Text(text)
    }
}
