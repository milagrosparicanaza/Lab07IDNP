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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

data class Paciente(
    val nombre: String,
    val apellido: String,
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
                    ListaPacientes()
                }
            }
        }
    }
}

@Composable
fun ListaPacientes() {
    // Lista de pacientes generada dinámicamente
    val pacientes = remember {
        List(20) { index ->
            Paciente(
                nombre = "Paciente$index",
                apellido = "Apellido$index",
                edad = (18..65).random(),
                avatarUrl = "https://randomuser.me/api/portraits/men/${index + 1}.jpg"
            )
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(pacientes) { paciente ->
            TarjetaPaciente(paciente)
        }
    }
}

@Composable
fun TarjetaPaciente(paciente: Paciente) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(paciente.avatarUrl),
                contentDescription = "Avatar del paciente",
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(
                    text = "${paciente.nombre} ${paciente.apellido}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Edad: ${paciente.edad} años", fontSize = 14.sp)
                Text(text = "Monitoreo activo: Detección de anemia", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListaPacientes() {
    MaterialTheme {
        ListaPacientes()
    }
}
