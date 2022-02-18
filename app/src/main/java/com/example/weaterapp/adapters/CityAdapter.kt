package com.example.weaterapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weaterapp.databinding.CityItemBinding
import com.example.weaterapp.models.City

class CityAdapter: RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    var cityList: List<City> = emptyList()
    set(newValue) {
        field = newValue
        notifyDataSetChanged()
    }

    inner class CityViewHolder( val binding: CityItemBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currentItem = cityList[position]
        holder.binding.cityName.text = currentItem.cityName
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}