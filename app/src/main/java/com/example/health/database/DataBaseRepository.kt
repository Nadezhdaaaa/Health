package com.example.health.database

import androidx.lifecycle.LiveData
import com.example.health.models.Dish

interface DataBaseRepository {
    val allDishes: LiveData<List<Dish>>
    suspend fun insert(dish: Dish, onSuccess: () -> Unit)
    suspend fun delete(dish: Dish, onSuccess: () -> Unit)
}