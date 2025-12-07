package com.example.listapacientesanemia.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.listapacientesanemia.MainActivity


@Composable
fun TemporizadorScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as MainActivity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Temporizador Persistente", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { activity.startTimer() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Temporizador")
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = { activity.stopTimer() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
        ) {
            Text("Detener Temporizador")
        }

        Spacer(Modifier.height(20.dp))
        Button(onClick = { navController.navigate("menu") }) {
            Text("Volver al menú")
        }
    }
}
