package com.example.weaterapp.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weaterapp.MainActivity
import com.example.weaterapp.R
import com.example.weaterapp.WeatherViewModel
import com.example.weaterapp.adapters.SearchAdapter
import com.example.weaterapp.databinding.FragmentSearchBinding
import com.example.weaterapp.models.City

class SearchFragment : Fragment(), SearchView.OnQueryTextListener,
    SearchAdapter.OnItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        viewModel = (activity as MainActivity).viewModel
        setupRV()
        return view
    }

    private fun setupRV() {
        searchAdapter = SearchAdapter(this)
        binding.searchRv.adapter = searchAdapter
        binding.searchRv.layoutManager = LinearLayoutManager(activity)
//        binding.searchRv.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_tb, menu)

        val search = menu.findItem(R.id.app_bar_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewModel.searchLocationByName(query)
            viewModel.searchResult.observe(viewLifecycleOwner) {
                binding.progressBar.isVisible = true
                searchAdapter.resultItems = it
                binding.progressBar.isVisible = false
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onItemClick(position: Int) {
        val currentItem = searchAdapter.resultItems[position]
        val lat = currentItem.lat
        val lon = currentItem.lon
        val cityName = currentItem.name
        viewModel.getWeatherModel(lat = lat, lon = lon)
        val city = City(id = null, lat = lat, lon = lon, cityName = cityName)
        viewModel.addCity(city)
        findNavController().navigate(R.id.action_editFragment_to_currentWeatherFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}