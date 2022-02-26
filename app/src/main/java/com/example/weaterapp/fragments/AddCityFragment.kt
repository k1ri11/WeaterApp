package com.example.weaterapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weaterapp.MainActivity
import com.example.weaterapp.R
import com.example.weaterapp.WeatherViewModel
import com.example.weaterapp.adapters.CityAdapter
import com.example.weaterapp.databinding.FragmentAddCityBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.util.*

class AddCityFragment : Fragment(), CityAdapter.OnItemClickListener {

    private var _binding: FragmentAddCityBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel
    private lateinit var cityAdapter: CityAdapter

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val startPosition = viewHolder.adapterPosition
            val endPosition = target.adapterPosition
            val oldCityId = cityAdapter.cityList[startPosition].id
            val newCityId = cityAdapter.cityList[endPosition].id
            cityAdapter.cityList[startPosition].id = newCityId
            cityAdapter.cityList[endPosition].id = oldCityId
            Collections.swap(cityAdapter.cityList, startPosition, endPosition)
            cityAdapter.notifyItemMoved(startPosition, endPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.updateCities(cityAdapter.cityList)
            val deletedCity = cityAdapter.cityList[viewHolder.adapterPosition]
            viewModel.deleteCity(deletedCity)
            Snackbar.make(binding.cityRv, "deleted", Snackbar.LENGTH_LONG)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setAction("Отменить") {
                    viewModel.addCity(deletedCity)
                }.show()
        }
    }

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
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.cityRv)
        setupRV()
        updateUi()
        binding.fabCity.setOnClickListener {
            findNavController().navigate(R.id.action_addCityFragment_to_editFragment)
        }
    }

    private fun updateUi() {
        viewModel.allCities.observe(viewLifecycleOwner) {
            cityAdapter.cityList = it
        }
    }

    private fun setupRV() {
        cityAdapter = CityAdapter(this)
        binding.cityRv.adapter = cityAdapter
        binding.cityRv.layoutManager = LinearLayoutManager(activity)
    }

    override fun onItemClick(position: Int) {
        val currentItem = cityAdapter.cityList[position]
        viewModel.getWeatherModel(lat = currentItem.lat, lon = currentItem.lon)
        findNavController().navigate(R.id.action_addCityFragment_to_currentWeatherFragment)
    }

    override fun onPause() {
        super.onPause()
        viewModel.updateCities(cityAdapter.cityList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}