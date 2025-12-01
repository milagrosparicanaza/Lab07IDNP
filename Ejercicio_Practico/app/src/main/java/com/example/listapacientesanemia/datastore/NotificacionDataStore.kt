package com.example.listapacientesanemia.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

val Context.notificationDataStore by preferencesDataStore(name = "notificacion_prefs")

class NotificacionDataStore(private val context: Context) {

    companion object {
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
    }

    val estadoNotificacion: Flow<Boolean> =
        context.notificationDataStore.data.map { prefs ->
            prefs[NOTIFICATION_ENABLED] ?: true   // Activado por defecto
        }

    suspend fun guardarEstado(estado: Boolean) {
        context.notificationDataStore.edit { prefs ->
            prefs[NOTIFICATION_ENABLED] = estado
        }
    }
}
