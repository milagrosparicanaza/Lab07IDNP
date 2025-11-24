package com.example.listapacientesanemia.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anemia_results")
data class AnemiaResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val paciente: String,
    val hemoglobina: Double,
    val resultado: String,
    val fecha: String
)
