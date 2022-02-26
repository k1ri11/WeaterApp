package com.example.weaterapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weaterapp.databinding.SearchItemBinding
import com.example.weaterapp.modelsApi.Search.SearchResultItem

class SearchAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var resultItems = emptyList<SearchResultItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {
        }
    }

    inner class SearchViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return resultItems.size
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val currentItem = resultItems[position]
        holder.binding.apply {
            name.text = currentItem.name.plus(",")
            state.text = currentItem.state?.plus(",")
            country.text = currentItem.country
        }
    }
}