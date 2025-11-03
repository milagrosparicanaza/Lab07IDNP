package com.example.listapacientesanemia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

// Modelo de datos para cada paciente
data class Paciente(
    val nombre: String,
    val edad: Int,
    val avatarUrl: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListaPacientesScreen()
                }
            }
        }
    }
}

@Composable
fun ListaPacientesScreen() {
    // Lista de pacientes simulada
    val pacientes = remember {
        listOf(
            Paciente("Juan Rosales", 30, "https://randomuser.me/api/portraits/men/1.jpg"),
            Paciente("María López", 28, "https://randomuser.me/api/portraits/women/2.jpg"),
            Paciente("Carlos Vega", 35, "https://randomuser.me/api/portraits/men/3.jpg"),
            Paciente("Rosa Huaman", 42, "https://randomuser.me/api/portraits/women/4.jpg"),
            Paciente("Pedro Castillo", 50, "https://randomuser.me/api/portraits/men/5.jpg"),
            Paciente("Lucía Paredes", 25, "https://randomuser.me/api/portraits/women/6.jpg"),
            Paciente("Luis Ramos", 29, "https://randomuser.me/api/portraits/men/7.jpg"),
            Paciente("Daniela Torres", 33, "https://randomuser.me/api/portraits/women/8.jpg"),
            Paciente("Jorge Flores", 40, "https://randomuser.me/api/portraits/men/9.jpg"),
            Paciente("Elena Chávez", 27, "https://randomuser.me/api/portraits/women/10.jpg"),
            Paciente("Miguel Díaz", 36, "https://randomuser.me/api/portraits/men/11.jpg"),
            Paciente("Sofía Castro", 32, "https://randomuser.me/api/portraits/women/12.jpg"),
            Paciente("Ricardo Ramos", 44, "https://randomuser.me/api/portraits/men/13.jpg"),
            Paciente("Andrea Flores", 22, "https://randomuser.me/api/portraits/women/14.jpg"),
            Paciente("José Valdivia", 39, "https://randomuser.me/api/portraits/men/15.jpg"),
            Paciente("Laura Ortiz", 31, "https://randomuser.me/api/portraits/women/16.jpg"),
            Paciente("David Molina", 37, "https://randomuser.me/api/portraits/men/17.jpg"),
            Paciente("Camila Gutiérrez", 26, "https://randomuser.me/api/portraits/women/18.jpg"),
            Paciente("Héctor Ramos", 43, "https://randomuser.me/api/portraits/men/19.jpg"),
            Paciente("Valeria Jiménez", 29, "https://randomuser.me/api/portraits/women/20.jpg")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Pacientes - Control de Anemia",
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
}

@Composable
fun PacienteCard(paciente: Paciente) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(paciente.avatarUrl),
                contentDescription = "Avatar de ${paciente.nombre}",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 12.dp)
            )

            Column {
                Text(
                    text = paciente.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Edad: ${paciente.edad} años",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPrevia() {
    MaterialTheme {
        ListaPacientesScreen()
    }
}
