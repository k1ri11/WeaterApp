package com.example.weaterapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weaterapp.databinding.DaysItemBinding
import com.example.weaterapp.models.Daily

class DiffUtilDailyCallback(
    private var oldList: List<Daily>,
    private var newList: List<Daily>
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

class DailyAdapter : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    var dailyList: List<Daily> = emptyList()
        set(newValue) {
            val diffCallback = DiffUtilDailyCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class DailyViewHolder(val binding: DaysItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding = DaysItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val currentItem = dailyList[position]
        holder.binding.apply {
            daysItemDate.text = currentItem.date
            Glide.with(this.root).load(currentItem.weatherIcon).into(daysItemIcon)
            dayTemp.text = currentItem.tempDay
            nightTemp.text = currentItem.tempNight
        }
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }
}