package com.example.listapacientesanemia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.listapacientesanemia.model.Paciente

@Composable
fun ListaPacientesScreen(navController: NavController) {

    val pacientes = remember {
        listOf(
            Paciente("Juan Rosales", 30, "https://randomuser.me/api/portraits/men/1.jpg", true),
            Paciente("María López", 28, "https://randomuser.me/api/portraits/women/2.jpg", false),
            Paciente("Carlos Vega", 35, "https://randomuser.me/api/portraits/men/3.jpg", true),
            Paciente("Rosa Huamán", 42, "https://randomuser.me/api/portraits/women/4.jpg", true),
            Paciente("Pedro Castillo", 50, "https://randomuser.me/api/portraits/men/5.jpg", false),
            Paciente("Lucía Paredes", 25, "https://randomuser.me/api/portraits/women/6.jpg", true),
            Paciente("Luis Ramos", 29, "https://randomuser.me/api/portraits/men/7.jpg", false),
            Paciente("Daniela Torres", 33, "https://randomuser.me/api/portraits/women/8.jpg", true),
            Paciente("Jorge Flores", 40, "https://randomuser.me/api/portraits/men/9.jpg", true),
            Paciente("Elena Chávez", 27, "https://randomuser.me/api/portraits/women/10.jpg", false)
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Lista de Pacientes - Monitoreo de Anemia",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(pacientes) { paciente ->
                    PacienteCard(paciente)
                }
            }
        }

        Button(
            onClick = { navController.navigate("theme") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Cambiar Tema")
        }
    }
}

@Composable
fun PacienteCard(paciente: Paciente) {

    val colorEstado = if (paciente.monitoreoActivo) Color(0xFF4CAF50) else Color(0xFFD32F2F)
    val textoEstado = if (paciente.monitoreoActivo) "Monitoreo Activo" else "Sin Monitoreo"

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(paciente.avatarUrl),
                contentDescription = paciente.nombre,
                modifier = Modifier.size(60.dp).padding(end = 12.dp)
            )

            Column(Modifier.weight(1f)) {
                Text(paciente.nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Edad: ${paciente.edad} años", fontSize = 14.sp, color = Color.DarkGray)

                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(colorEstado.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(textoEstado, color = colorEstado, fontSize = 12.sp)
                }
            }
        }
    }
}
