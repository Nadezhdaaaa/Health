package com.example.health.database.room

import androidx.lifecycle.LiveData
import com.example.health.database.DataBaseRepository
import com.example.health.models.Dish

class FoodRoomRepository(
    private val foodRoomDao: FoodRoomDao
) : DataBaseRepository {

    override val allDishes: LiveData<List<Dish>>
        get() = foodRoomDao.getAllDishes()

    override suspend fun insert(dish: Dish, onSuccess: () -> Unit) {
        foodRoomDao.insert(dish)
        onSuccess()
    }

    override suspend fun delete(dish: Dish, onSuccess: () -> Unit) {
        foodRoomDao.delete(dish)
        onSuccess()
    }
}