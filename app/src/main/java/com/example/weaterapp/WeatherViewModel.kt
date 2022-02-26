package com.example.weaterapp

import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weaterapp.models.City
import com.example.weaterapp.models.WeatherModel
import com.example.weaterapp.modelsApi.Search.SearchResult
import com.example.weaterapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class WeatherViewModel(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {

    val weather: MutableLiveData<WeatherModel> = MutableLiveData()
    val searchResult: MutableLiveData<SearchResult> = MutableLiveData()
    val allCities: LiveData<List<City>> = repository.allCities

    fun getWeatherModel(lat: Double, lon: Double) {
        viewModelScope.launch {
            if (hasInternetConnection()) {
                val response = repository.getWeatherModel(lat, lon).toWeatherModel()
                response.location = getCityNameAndCountry(lat, lon)
                weather.value = response
            } else {
                Toast.makeText(
                    getApplication<WeatherApplication>(),
                    "Нет доступа в интренет",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun searchLocationByName(query: String) {
        viewModelScope.launch {
            if (hasInternetConnection()) {
                val response = repository.searchLocationByName(query)
                searchResult.value = response
            } else {
                Toast.makeText(
                    getApplication<WeatherApplication>(),
                    "Нет доступа в интренет",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getCityNameAndCountry(lat: Double, lon: Double): String {
        val geoCoder = Geocoder(getApplication<WeatherApplication>(), Locale.getDefault())
        val adress = geoCoder.getFromLocation(lat, lon, 3)
        val cityName = adress[0].locality
        val countryName = adress[0].countryName
        Log.d("TAG", "getCityNameAndCountry: $cityName $countryName")
        return "$cityName, $countryName"
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<WeatherApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun addCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCity(city)
        }
    }

    fun updateCities(new_cities :List<City>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCities(new_cities)
        }
    }

    fun deleteCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCity(city)
        }
    }
}