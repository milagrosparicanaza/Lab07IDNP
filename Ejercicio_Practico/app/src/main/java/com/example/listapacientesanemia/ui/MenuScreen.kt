package com.example.listapacientesanemia.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hola, Milagros",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

                MenuCard(
                    texto = "Lista de Pacientes",
                    icono = Icons.Default.List,
                    color = Color(0xFFEF9A9A),
                ) { navController.navigate("home") }

                MenuCard(
                    texto = "Control de Anemia",
                    icono = Icons.Default.HealthAndSafety,
                    color = Color(0xFF80DEEA),
                ) { navController.navigate("control_anemia") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

                MenuCard(
                    texto = "Prueba de Anemia",
                    icono = Icons.Default.Bloodtype,
                    color = Color(0xFFCE93D8),
                ) { navController.navigate("prueba_anemia") }

                MenuCard(
                    texto = "Prevención",
                    icono = Icons.Default.Vaccines,
                    color = Color(0xFFA5D6A7),
                ) { navController.navigate("prevencion") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

                MenuCard(
                    texto = "Notificaciones",
                    icono = Icons.Default.Notifications,
                    color = Color(0xFFFFF59D),
                ) { navController.navigate("notificaciones") }

            }

        }

        Spacer(modifier = Modifier.height(40.dp))

        // Botón cerrar sesión
        Button(
            onClick = { /* acción futura */ },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE8EAF6)
            ),
            modifier = Modifier.width(220.dp)
        ) {
            Text(text = "Cerrar sesión", color = Color(0xFF1B5E20))
        }
    }
}

@Composable
fun MenuCard(texto: String, icono: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .size(140.dp)
            .background(color, RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            icono,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(48.dp)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = texto,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
