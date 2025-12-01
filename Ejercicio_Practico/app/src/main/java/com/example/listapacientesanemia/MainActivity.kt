package com.example.listapacientesanemia

import android.Manifest
import android.os.Build
import android.os.Bundle
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
import com.example.listapacientesanemia.datastore.ThemeDataStore
import com.example.listapacientesanemia.model.AnemiaRepository
import com.example.listapacientesanemia.model.AppDatabase
import com.example.listapacientesanemia.ui.*
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModel
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModelFactory
import com.example.listapacientesanemia.work.RecordatorioWorker
import java.util.concurrent.TimeUnit
import android.util.Log

data class Paciente(
    val nombre: String,
    val edad: Int,
    val avatarUrl: String,
    val monitoreoActivo: Boolean
)

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Programar notificación repetitiva
        programarRecordatorioSemanal()
        WorkManager.getInstance(this)
            .getWorkInfosForUniqueWorkLiveData("recordatorio_anemia")
            .observe(this) { workInfos ->
                if (workInfos.isNotEmpty()) {
                    val estado = workInfos[0].state
                    Log.d("WORK_STATE", "Estado actual: $estado")
                    Log.d("WORKER_STATUS", "Programando Worker cada 1 minuto...")

                }
            }

        val testWork = OneTimeWorkRequestBuilder<RecordatorioWorker>().build()
        WorkManager.getInstance(this).enqueue(testWork)
        Log.d("WORKER_STATUS", "OneTimeWorkRequest enviado para prueba inmediata")



        // THEME DATASTORE
        val themeStore = ThemeDataStore(this)

        // ROOM
        val db = AppDatabase.getDatabase(this)
        val repo = AnemiaRepository(db.anemiaDao())

        // VIEWMODEL
        val anemiaViewModel = AnemiaViewModelFactory(repo)
            .create(AnemiaViewModel::class.java)

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
                }
            }
        }
    }

    private fun programarRecordatorioSemanal() {

        val workRequest =
            PeriodicWorkRequestBuilder<RecordatorioWorker>(
                15, TimeUnit.MINUTES
            ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "recordatorio_anemia",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
