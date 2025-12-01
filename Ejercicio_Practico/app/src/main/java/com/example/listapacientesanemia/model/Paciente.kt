package com.example.listapacientesanemia.model

data class Paciente(
    val nombre: String,
    val edad: Int,
    val avatarUrl: String,
    val monitoreoActivo: Boolean
)
