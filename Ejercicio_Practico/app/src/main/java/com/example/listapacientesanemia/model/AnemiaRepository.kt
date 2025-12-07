package com.example.listapacientesanemia.model

import kotlinx.coroutines.flow.Flow


class AnemiaRepository(private val dao: AnemiaDao) {

    fun getAllResultados(): Flow<List<AnemiaResult>> {
        return dao.getAllResultados()
    }

    suspend fun insert(resultado: AnemiaResult) {
        dao.insertResultado(resultado)
    }
    suspend fun getUltimoResultado(): AnemiaResult? {
        return dao.getUltimoResultado()
    }

}
