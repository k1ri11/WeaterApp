package com.example.weaterapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weaterapp.repository.Repository

class WeatherViewModelFactory(
    val app: Application,
    val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(app, repository) as T
    }
}