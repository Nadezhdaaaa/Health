package com.example.health.screens.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.health.databinding.ItemFoodBinding
import com.example.health.models.Dish

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodHolder>() {

    class FoodHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)

    private var mListFood = emptyList<Dish>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)
        return FoodHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        val food = mListFood[position]
        holder.binding.foodItemName.text = food.name
    }

    override fun getItemCount(): Int = mListFood.size

    fun setList(list: List<Dish>) {
        mListFood = list
        notifyDataSetChanged()
    }
}