package com.example.weaterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.weaterapp.database.CityDatabase
import com.example.weaterapp.databinding.ActivityMainBinding
import com.example.weaterapp.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val repository = Repository(CityDatabase.getDatabase(this).cityDao())
        val viewModelFactory = WeatherViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
    }
}