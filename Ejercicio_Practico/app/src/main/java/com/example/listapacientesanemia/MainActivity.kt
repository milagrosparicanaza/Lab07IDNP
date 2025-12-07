package com.example.listapacientesanemia

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.example.listapacientesanemia.datastore.NotificacionDataStore
import com.example.listapacientesanemia.datastore.ThemeDataStore
import com.example.listapacientesanemia.model.*
import com.example.listapacientesanemia.ui.*
import com.example.listapacientesanemia.ui.notificaciones.NotificacionScreen
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModel
import com.example.listapacientesanemia.ui.viewmodel.AnemiaViewModelFactory
import com.example.listapacientesanemia.work.*
import java.util.concurrent.TimeUnit
import android.app.PendingIntent
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class MainActivity : ComponentActivity() {
    private var ultimaFechaRegistro: Long = 0L


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    // ========= TEMPORIZADOR =========
    private val timerChannelId = "timer_channel"
    private val timerNotificationId = 2001

    private val handler = Handler(Looper.getMainLooper())
    private var timerRunning = false
    private var startTime = 0L

    private val timerRunnable = object : Runnable {
        override fun run() {
            if (timerRunning) {
                val elapsed = (SystemClock.elapsedRealtime() - startTime) / 1000
                Log.d("TIMER", "Segundos: $elapsed")

                updateTimerNotification(elapsed)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (intent?.getBooleanExtra("STOP_TIMER", false) == true) {
            stopTimer()
        }

        // Permisos Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Datastore
        val notiStore = NotificacionDataStore(this)
        val themeStore = ThemeDataStore(this)

        // ROOM
        val db = AppDatabase.getDatabase(this)
        val repo = AnemiaRepository(db.anemiaDao())
        lifecycleScope.launch(Dispatchers.IO) {
            cargarUltimaFechaRegistro(repo)
        }


        // ViewModel
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
                    composable("theme") { ThemeScreen(navController, themeStore) }
                    composable("control_anemia") { ControlAnemiaScreen(navController) }
                    composable("prueba_anemia") { PruebaAnemiaScreen() }
                    composable("prevencion") { PrevencionScreen() }
                    composable("registroResultado") {
                        RegistroResultadoScreen(navController, anemiaViewModel)
                    }
                    composable("listaResultados") {
                        ListaResultadosScreen(navController, anemiaViewModel)
                    }
                    composable("notificaciones") {
                        NotificacionScreen(
                            navController,
                            notiStore,
                            activarNotificaciones = { activarRecordatorio() },
                            desactivarNotificaciones = { cancelarRecordatorio() }
                        )
                    }
                    composable("temporizador") {
                        TemporizadorScreen(navController)
                    }
                }
            }
        }
    }

    // ========= RECORDATORIOS =========

    private fun activarRecordatorio() {
        val workRequest = PeriodicWorkRequestBuilder<RecordatorioWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "recordatorio_anemia",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )

        Log.d("WORKER_STATUS", "Recordatorio ACTIVADO")
    }

    private fun cancelarRecordatorio() {
        WorkManager.getInstance(this).cancelUniqueWork("recordatorio_anemia")
        Log.d("WORKER_STATUS", "Recordatorio DESACTIVADO")
    }

    // ========= TEMPORIZADOR NOTIFICACIÓN =========

    private fun createTimerChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                timerChannelId,
                "Temporizador",
                android.app.NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(android.app.NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun startTimer() {
        if (timerRunning) return

        createTimerChannel()

        timerRunning = true
        startTime = SystemClock.elapsedRealtime()

        updateTimerNotification(0)   // ya aparece
        handler.post(timerRunnable)

        Log.d("TIMER", "Iniciado")
    }

    fun stopTimer() {
        timerRunning = false
        handler.removeCallbacks(timerRunnable)

        NotificationManagerCompat.from(this).cancel(timerNotificationId)
        Log.d("TIMER", "Detenido")
    }

    private fun updateTimerNotification(seconds: Long) {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60
        val time = String.format("%02d:%02d:%02d", h, m, s)

        val tiempoUltimo = tiempoDesdeUltimoRegistro()

        val notification = NotificationCompat.Builder(this, timerChannelId)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("Temporizador activo")
            .setContentText("Transcurrido: $time")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "Transcurrido: $time\nÚltimo registro: $tiempoUltimo"
                )
            )
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()

        NotificationManagerCompat.from(this)
            .notify(timerNotificationId, notification)
    }



    private suspend fun cargarUltimaFechaRegistro(repo: AnemiaRepository) {
        val ultimo = repo.getUltimoResultado()
        if (ultimo != null) {
            val fecha = ultimo.fecha
            val formatos = listOf(
                "dd/MM/yyyy",
                "yyyy-MM-dd",
                "yyyy-MM-dd HH:mm",
                "dd-MM-yyyy"
            )

            for (f in formatos) {
                try {
                    val sdf = SimpleDateFormat(f, Locale.getDefault())
                    ultimaFechaRegistro = sdf.parse(fecha)?.time ?: continue
                    return
                } catch (_: Exception) { }
            }

            ultimaFechaRegistro = 0L // si no se pudo interpretar
        }
    }
    private fun tiempoDesdeUltimoRegistro(): String {
        if (ultimaFechaRegistro == 0L) return "Sin registros todavía"

        val ahora = System.currentTimeMillis()
        val diff = ahora - ultimaFechaRegistro

        val dias = diff / (1000 * 60 * 60 * 24)
        val horas = (diff / (1000 * 60 * 60)) % 24
        val minutos = (diff / (1000 * 60)) % 60

        return when {
            dias > 0 -> "Hace $dias días $horas horas"
            horas > 0 -> "Hace $horas horas $minutos min"
            minutos > 0 -> "Hace $minutos min"
            else -> "Hace menos de 1 min"
        }
    }




}
