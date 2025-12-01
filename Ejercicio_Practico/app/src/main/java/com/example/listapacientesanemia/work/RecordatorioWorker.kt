package com.example.listapacientesanemia.work

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.listapacientesanemia.MainActivity
import com.example.listapacientesanemia.R
import android.util.Log

class RecordatorioWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("WORKER_STATUS", "El Worker se ejecutó correctamente")

        crearCanalNotificacion()

        // 🔥 Intent para abrir la app al tocar la notificación
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("open", "registroResultado") // Para navegar si deseas
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 🔥 Notificación profesional
        val builder = NotificationCompat.Builder(applicationContext, "recordatorio_channel")
            .setSmallIcon(R.drawable.baseline_health_and_safety_24)   // Usa tu icono
            .setContentTitle("Control de Hemoglobina")
            .setContentText("Ya pasó una semana. Registra tu nuevo nivel para seguir tu monitoreo.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)                           // se cierra al tocar
            .setContentIntent(pendingIntent)               // abre la app
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "Ya pasó una semana desde tu último registro. Mantén tu monitoreo actualizado ingresando tu nivel actual de hemoglobina."
                )
            )

        // 🔥 Verificar permiso en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.success()
        }

        NotificationManagerCompat.from(applicationContext).notify(1, builder.build())

        Log.d("WORKER_STATUS", "El Worker terminó su ejecución correctamente")

        return Result.success()
    }

    private fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "recordatorio_channel",
                "Recordatorios de Anemia",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Notificaciones semanales sobre control de hemoglobina"

            val manager = applicationContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}

