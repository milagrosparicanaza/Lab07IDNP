package com.example.listapacientesanemia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.listapacientesanemia.model.AnemiaRepository

class AnemiaViewModelFactory(
    private val repo: AnemiaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnemiaViewModel(repo) as T
    }
}
