package com.example.listapacientesanemia

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import com.example.listapacientesanemia.datastore.NotificacionDataStore
import com.example.listapacientesanemia.datastore.ThemeDataStore
import com.example.listapacientesanemia.model.AnemiaRepository
import com.example.listapacientesanemia.model.AppDatabase
import com.example.listapacientesanemia.ui.*
import com.example.listapacientesanemia.ui.notificaciones.NotificacionScreen
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModel
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModelFactory
import com.example.listapacientesanemia.work.RecordatorioWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // PERMISO PARA NOTIFICACIONES (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // DATASTORE PARA NOTIFICACIONES
        val notiStore = NotificacionDataStore(this)

        // Observa cambios en WorkManager (debug)
        WorkManager.getInstance(this)
            .getWorkInfosForUniqueWorkLiveData("recordatorio_anemia")
            .observe(this) { info ->
                if (!info.isNullOrEmpty()) {
                    Log.d("WORK_STATE", "Estado actual: ${info[0].state}")
                }
            }

        // THEME DATASTORE
        val themeStore = ThemeDataStore(this)

        // ROOM
        val db = AppDatabase.getDatabase(this)
        val repo = AnemiaRepository(db.anemiaDao())

        // VIEWMODEL
        val anemiaViewModel = AnemiaViewModelFactory(repo)
            .create(AnemiaViewModel::class.java)

        // Contenido Compose
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

                    composable("menu") { MenuScreen(navController) }

                    composable("home") { ListaPacientesScreen(navController) }

                    composable("theme") {
                        ThemeScreen(navController, themeStore)
                    }

                    composable("control_anemia") {
                        ControlAnemiaScreen(navController)
                    }

                    composable("prueba_anemia") {
                        PruebaAnemiaScreen()
                    }

                    composable("prevencion") {
                        PrevencionScreen()
                    }

                    composable("registroResultado") {
                        RegistroResultadoScreen(navController, anemiaViewModel)
                    }

                    composable("listaResultados") {
                        ListaResultadosScreen(navController, anemiaViewModel)
                    }

                    // 🔥 NUEVA PANTALLA DE NOTIFICACIONES
                    composable("notificaciones") {
                        NotificacionScreen(
                            navController = navController,
                            dataStore = notiStore,
                            activarNotificaciones = { activarRecordatorio() },
                            desactivarNotificaciones = { cancelarRecordatorio() }
                        )
                    }
                }
            }
        }
    }

    // PROGRAMAR RECORDATORIO
    private fun activarRecordatorio() {
        val workRequest = PeriodicWorkRequestBuilder<RecordatorioWorker>(
            15, TimeUnit.MINUTES   // mínimo permitido por WorkManager
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "recordatorio_anemia",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )

        Log.d("WORKER_STATUS", "Recordatorio ACTIVADO")
    }

    // CANCELAR RECORDATORIO
    private fun cancelarRecordatorio() {
        WorkManager.getInstance(this)
            .cancelUniqueWork("recordatorio_anemia")

        Log.d("WORKER_STATUS", "Recordatorio DESACTIVADO")
    }
}
