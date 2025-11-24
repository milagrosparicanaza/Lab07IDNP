package com.example.listapacientesanemia.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnemiaDao {

    @Insert
    suspend fun insertResultado(resultado: AnemiaResult)

    @Query("SELECT * FROM anemia_results ORDER BY id DESC")
    fun obtenerResultados(): Flow<List<AnemiaResult>>
}
