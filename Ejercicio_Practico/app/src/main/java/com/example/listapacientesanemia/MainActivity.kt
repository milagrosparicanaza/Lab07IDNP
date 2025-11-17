package com.example.listapacientesanemia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listapacientesanemia.datastore.ThemeDataStore
import com.example.listapacientesanemia.ui.ListaPacientesScreen
import com.example.listapacientesanemia.ui.ThemeScreen

// ✅ Modelo de datos de Paciente
data class Paciente(
    val nombre: String,
    val edad: Int,
    val avatarUrl: String,
    val monitoreoActivo: Boolean // NUEVO: estado de detección de anemia
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val themeStore = ThemeDataStore(this)

        setContent {

            // Observa si el tema debe ser oscuro
            val isDark by themeStore.themeFlow.collectAsState(initial = false)

            MaterialTheme(
                colorScheme = if (isDark) darkColorScheme() else lightColorScheme()
            ) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {

                    composable("home") {
                        ListaPacientesScreen(navController)
                    }

                    composable("theme") {
                        ThemeScreen(
                            navController = navController,
                            themeDataStore = themeStore
                        )
                    }
                }
            }
        }
    }
}

