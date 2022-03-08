package com.example.weaterapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weaterapp.databinding.CityItemBinding
import com.example.weaterapp.models.City

class CityAdapter(
    private val listener: OnItemClickListener
): RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    var cityList: List<City> = emptyList()
    set(newValue) {
        field = newValue
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    inner class CityViewHolder( val binding: CityItemBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(bindingAdapterPosition)
        }
    }

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