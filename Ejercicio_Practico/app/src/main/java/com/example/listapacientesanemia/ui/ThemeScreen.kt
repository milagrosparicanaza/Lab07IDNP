package com.example.listapacientesanemia.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.listapacientesanemia.datastore.ThemeDataStore
import kotlinx.coroutines.launch

@Composable
fun ThemeScreen(navController: NavController, themeDataStore: ThemeDataStore) {

    val scope = rememberCoroutineScope()
    var isDark by remember { mutableStateOf(false) }

    // Obtener tema guardado
    LaunchedEffect(true) {
        themeDataStore.themeFlow.collect { saved ->
            isDark = saved
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("Seleccionar Tema", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text("Modo oscuro")
            Switch(
                checked = isDark,
                onCheckedChange = {
                    isDark = it
                    scope.launch {
                        themeDataStore.saveTheme(it)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text("Volver a la lista")
        }
    }
}
