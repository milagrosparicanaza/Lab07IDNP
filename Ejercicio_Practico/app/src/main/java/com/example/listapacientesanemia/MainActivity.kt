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
import com.example.listapacientesanemia.ui.MenuScreen
import com.example.listapacientesanemia.ui.*
import com.example.listapacientesanemia.ui.ControlAnemiaScreen
import com.example.listapacientesanemia.ui.PruebaAnemiaScreen
import com.example.listapacientesanemia.ui.PrevencionScreen
import com.example.listapacientesanemia.model.AppDatabase
import com.example.listapacientesanemia.model.AnemiaRepository
import com.example.listapacientesanemia.ui.ListaResultadosScreen
import com.example.listapacientesanemia.ui.RegistroResultadoScreen
import com.example.listapacientesanemia.model.*


data class Paciente(
    val nombre: String,
    val edad: Int,
    val avatarUrl: String,
    val monitoreoActivo: Boolean
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val themeStore = ThemeDataStore(this)
        val db = AppDatabase.getDatabase(this)
        val repo = AnemiaRepository(db.anemiaDao())

        setContent {

            val isDark by themeStore.themeFlow.collectAsState(initial = false)

            MaterialTheme(
                colorScheme = if (isDark) darkColorScheme() else lightColorScheme()
            ) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "menu"
                ) {

                    composable("menu") {
                        MenuScreen(navController)
                    }

                    composable("home") {
                        ListaPacientesScreen(navController)
                    }

                    composable("theme") {
                        ThemeScreen(
                            navController = navController,
                            themeDataStore = themeStore
                        )
                    }


                    composable("control_anemia") { ControlAnemiaScreen() }
                    composable("prueba_anemia") { PruebaAnemiaScreen() }
                    composable("prevencion") { PrevencionScreen() }

                    composable("registroResultado") {
                        RegistroResultadoScreen(navController, repo)
                    }

                    composable("listaResultados") {
                        ListaResultadosScreen(navController, repo)
                    }
                }

            }
        }
    }
}

