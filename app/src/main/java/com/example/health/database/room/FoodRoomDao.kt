package com.example.health.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.health.models.Dish
import com.example.health.models.DishCreateRecyclerViewTuple

@Dao
interface FoodRoomDao {
    @Query("SELECT * from food_table")
    fun getAllDishes():LiveData<List<Dish>>

    @Query("SELECT id, name FROM food_table WHERE name = :name")
    suspend fun findByName(name: String) : DishCreateRecyclerViewTuple?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dish: Dish)

    @Delete
    suspend fun delete(dish: Dish)
}