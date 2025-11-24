package com.example.listapacientesanemia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listapacientesanemia.model.AnemiaRepository
import com.example.listapacientesanemia.model.AnemiaResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnemiaViewModel(private val repository: AnemiaRepository) : ViewModel() {

    private val _resultados = MutableStateFlow<List<AnemiaResult>>(emptyList())
    val resultados: StateFlow<List<AnemiaResult>> = _resultados

    init {
        cargarResultados()
    }

    fun cargarResultados() {
        viewModelScope.launch {
            repository.getAllResultados().collect {
                _resultados.value = it
            }
        }
    }

    fun guardarResultado(
        paciente: String,
        nivelHemoglobina: Float,
        resultado: String,
        fecha: String
    ) {
        viewModelScope.launch {
            val nuevo = AnemiaResult(
                paciente = paciente,
                hemoglobina = nivelHemoglobina.toDouble(),
                resultado = resultado,
                fecha = fecha
            )
            repository.insert(nuevo)
        }
    }
}
