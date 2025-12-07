package com.example.listapacientesanemia.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnemiaDao {

    @Query("SELECT * FROM anemia_results ORDER BY fecha DESC")
    fun getAllResultados(): Flow<List<AnemiaResult>>

    @Insert
    suspend fun insertResultado(result: AnemiaResult)

    @Query("SELECT * FROM anemia_results ORDER BY id DESC LIMIT 1")
    suspend fun getUltimoResultado(): AnemiaResult?

}

