package com.example.listapacientesanemia.model

class AnemiaRepository(private val dao: AnemiaDao) {

    val resultados = dao.obtenerResultados()

    suspend fun guardarResultado(resultado: AnemiaResult) {
        dao.insertResultado(resultado)
    }
}
