package com.example.weaterapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weaterapp.databinding.HoursItemBinding
import com.example.weaterapp.models.Hourly

class DiffUtilHourlyCallback(
    private var oldList: List<Hourly>,
    private var newList: List<Hourly>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]
        return oldItem == newItem
    }
}

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    var hourlyList: List<Hourly> = emptyList()
        set(newValue) {
            val diffCallback = DiffUtilHourlyCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class HourlyViewHolder(val binding: HoursItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding = HoursItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val currentItem = hourlyList[position]
        holder.binding.apply {
            hoursItemTime.text = currentItem.date
            Glide.with(this.root).load(currentItem.weatherIcon).into(hoursItemIcon)
            hoursItemTemp.text = currentItem.temp
        }
    }

    override fun getItemCount(): Int {
        return hourlyList.size
    }
}