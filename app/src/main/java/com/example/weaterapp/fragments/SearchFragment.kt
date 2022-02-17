package com.example.weaterapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weaterapp.MainActivity
import com.example.weaterapp.R
import com.example.weaterapp.WeatherViewModel
import com.example.weaterapp.adapters.SearchAdapter
import com.example.weaterapp.databinding.FragmentEditBinding
import com.example.weaterapp.modelsApi.Search.SearchResult
import com.example.weaterapp.modelsApi.Search.SearchResultItem
import java.util.ArrayList

class SearchFragment : Fragment(), SearchView.OnQueryTextListener, SearchAdapter.OnItemClickListener {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                searchAdapter.resultItems = it as ArrayList<SearchResultItem>
                binding.progressBar.isVisible = false
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onItemClick(position: Int) {
        val lat = viewModel.searchResult.value?.get(position)?.lat
        val lon = viewModel.searchResult.value?.get(position)?.lon
        if (lat!= null && lon!= null){
            viewModel.getWeatherModel(lat = lat, lon = lon)
        }
        else{
            Toast.makeText(requireContext(), "Не удалось получить данные", Toast.LENGTH_LONG).show()
        }
        findNavController().navigate(R.id.action_editFragment_to_currentWeatherFragment)
    }
}