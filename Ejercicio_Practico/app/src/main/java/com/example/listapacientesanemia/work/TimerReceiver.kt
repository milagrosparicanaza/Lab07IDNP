package com.example.listapacientesanemia.work

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TimerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "STOP_TIMER") {
            Log.d("TIMER", "Acción STOP_TIMER recibida desde notificación")

            val stopIntent = Intent(context, com.example.listapacientesanemia.MainActivity::class.java)
            stopIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            stopIntent.putExtra("STOP_TIMER", true)
            context.startActivity(stopIntent)
        }
    }
}
