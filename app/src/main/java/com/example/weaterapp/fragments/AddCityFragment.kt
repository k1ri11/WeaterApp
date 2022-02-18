package com.example.weaterapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weaterapp.MainActivity
import com.example.weaterapp.R
import com.example.weaterapp.WeatherViewModel
import com.example.weaterapp.adapters.CityAdapter
import com.example.weaterapp.databinding.FragmentAddCityBinding
import com.example.weaterapp.databinding.FragmentCurrentWeatherBinding

class AddCityFragment : Fragment() {

    private var _binding: FragmentAddCityBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel
    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCityBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        updateUi()
        binding.fabCity.setOnClickListener{
            findNavController().navigate(R.id.action_addCityFragment_to_editFragment)
        }
    }

    private fun updateUi() {
        viewModel.allCities.observe(viewLifecycleOwner) {
            cityAdapter.cityList = it
        }
    }

    private fun setupRV() {
        cityAdapter = CityAdapter()
        binding.cityRv.adapter = cityAdapter
        binding.cityRv.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}