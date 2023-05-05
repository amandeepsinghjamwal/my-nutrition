package com.example.mynutrition

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynutrition.api.models.Item
import com.example.mynutrition.databinding.ItemsLayoutBinding
import java.util.Locale

class ItemListAdapter(context: Context, private val onItemClicked:(Item)-> Unit): ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallBack) {
    class ItemViewHolder(var binding: ItemsLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data:Item){
            binding.apply {
                binding.name.text=data.name.replaceFirstChar {
                    if (it.isLowerCase()){
                        it.titlecase(Locale.getDefault())
                    }
                    else{
                        it.toString()
                    }
                }
                binding.calories.text="${data.calories} cal"
                binding.quantity.text="per ${data.serving_size_g} g"
                binding.protein.text="${data.protein_g} g"
                binding.fats.text="${data.fat_total_g} g"
                binding.carbs.text="${data.carbohydrates_total_g} g"
            }
        }
    }
    companion object{
        private val DiffCallBack= object : DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.name==newItem.name
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem==newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemsLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current=getItem(position)
        holder.itemView.setOnClickListener{
            onItemClicked(current)
        }
        holder.bind(current)
    }
}