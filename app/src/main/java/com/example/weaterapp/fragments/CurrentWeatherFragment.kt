package com.example.weaterapp.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.weaterapp.MainActivity
import com.example.weaterapp.R
import com.example.weaterapp.WeatherViewModel
import com.example.weaterapp.adapters.DailyAdapter
import com.example.weaterapp.adapters.HourlyAdapter
import com.example.weaterapp.databinding.FragmentCurrentWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.log


class CurrentWeatherFragment : Fragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel

    private lateinit var hourlyAdapter: HourlyAdapter
    private lateinit var dailyAdapter: DailyAdapter

    private lateinit var fusedLocation: FusedLocationProviderClient

    private val requestLocationPermissionsLauncher = registerForActivityResult(
        RequestMultiplePermissions(),
        ::onGotPermissionsResult
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        viewModel = (activity as MainActivity).viewModel
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclers()
        updateUi()
        binding.refresh.setOnRefreshListener {
            refreshWeather()
            binding.refresh.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.current_tb, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_city -> findNavController().navigate(R.id.action_currentWeatherFragment_to_addCityFragment)
            R.id.location -> setWeatherWithCurrentLocation()
        }
        return true
    }

    private fun setWeatherWithCurrentLocation() {
        requestLocationPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun onGotPermissionsResult(grantResults: Map<String, Boolean>) {
        if (grantResults.entries.all { it.value }) {
            onPermissionsGranted()
        } else {
            Toast.makeText(activity, "no permissions", Toast.LENGTH_LONG).show()
        }
    }

    private fun onPermissionsGranted() {
        if (gpsEnabled()) {
            fusedLocation = LocationServices.getFusedLocationProviderClient(this.requireContext())
            fusedLocation.lastLocation
                .addOnSuccessListener {
//                    Log.d("TAG", "getUserLocation: ${it.latitude} ${it.longitude}")
                    getAndSetWeather(it.latitude, it.longitude)
                }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            //TODO поискать чтобы можно было включать всплывашкой а не переходом в настройки как сейчас
            Toast.makeText(activity, "включи gps", Toast.LENGTH_LONG).show()
        }
    }

    private fun gpsEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gpsEnabled && networkEnabled
    }

    private fun refreshWeather() {
        val lat = viewModel.weather.value?.lat
        val lon = viewModel.weather.value?.lon
        if (lat != null && lon != null) {
            getAndSetWeather(lat, lon)
        }
    }

    private fun getAndSetWeather(lat: Double, lon: Double) {
        showProgressBar()
        viewModel.getWeatherModel(lat, lon)
        updateUi()
        hideProgressBar()
    }

    private fun updateUi() {
        viewModel.weather.observe(viewLifecycleOwner, Observer { weatherModel ->
            hourlyAdapter.hourlyList = weatherModel.hourly
            dailyAdapter.dailyList = weatherModel.daily
            binding.apply {
                currentCity.text = weatherModel.location
                currentTemp.text = weatherModel.current.temp
                updatedTime.text = weatherModel.current.date
                Glide.with(this.root).load(weatherModel.current.weatherIcon).into(weatherIcon)
            }
        })
    }

    private fun setupRecyclers() {
        hourlyAdapter = HourlyAdapter()
        dailyAdapter = DailyAdapter()
        binding.apply {
            hourlyRv.adapter = hourlyAdapter
            hourlyRv.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            dailyRv.adapter = dailyAdapter
            dailyRv.layoutManager = LinearLayoutManager(activity)
            dailyRv.isNestedScrollingEnabled = false
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


