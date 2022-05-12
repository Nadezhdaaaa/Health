package com.example.health.screens.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.health.databinding.ItemFoodBinding
import com.example.health.models.Dish

interface FoodActionListener {
    fun onFoodAdd(dish: Dish)

    fun onFoodInfoDelete(dish: Dish)
}

class FoodAdapter(
    private val foodActionListener: FoodActionListener
) : RecyclerView.Adapter<FoodAdapter.FoodHolder>(), View.OnClickListener, View.OnLongClickListener {

    class FoodHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)

    private var mListFood = emptyList<Dish>()

    override fun onClick(v: View) {
        val dish = v.tag as Dish
        foodActionListener.onFoodAdd(dish)
    }

    override fun onLongClick(v: View): Boolean {
        val dish = v.tag as Dish
        foodActionListener.onFoodInfoDelete(dish)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)

        return FoodHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        val food = mListFood[position]
        holder.binding.foodItemName.text = food.name
        holder.itemView.tag = food
    }

    override fun getItemCount(): Int = mListFood.size

    fun setList(list: List<Dish>) {
        mListFood = list
        notifyDataSetChanged()
    }
}