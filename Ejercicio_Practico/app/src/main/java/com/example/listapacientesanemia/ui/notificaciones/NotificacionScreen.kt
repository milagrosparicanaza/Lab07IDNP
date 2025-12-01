package com.example.listapacientesanemia.ui.notificaciones

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.listapacientesanemia.datastore.NotificacionDataStore
import kotlinx.coroutines.launch

@Composable
fun NotificacionScreen(
    navController: NavController,
    dataStore: NotificacionDataStore,
    activarNotificaciones: () -> Unit,
    desactivarNotificaciones: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val estado by dataStore.estadoNotificacion.collectAsState(initial = true)

    Column(modifier = Modifier.padding(20.dp)) {

        Text(
            text = "Recordatorios de Hemoglobina",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        // -------- SWITCH ----------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Notificaciones semanales")
            Switch(
                checked = estado,
                onCheckedChange = { nuevoEstado ->
                    scope.launch {
                        dataStore.guardarEstado(nuevoEstado)

                        if (nuevoEstado) activarNotificaciones()
                        else desactivarNotificaciones()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Vista previa del recordatorio:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ---------- PREVIEW DE NOTIFICACIÓN ----------
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🔔 Control de Hemoglobina", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Ya pasó una semana desde tu último registro. Mantén tu monitoreo actualizado.")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
