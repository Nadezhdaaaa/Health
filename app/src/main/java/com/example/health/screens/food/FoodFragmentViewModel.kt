package com.example.health.screens.food

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.health.models.Dish
import com.example.health.utilits.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val allDishes = REPOSITORY.allDishes

    fun insert(dish: Dish, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.insert(dish){
                onSuccess()
            }
        }
    }

    fun delete(dish: Dish,onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.delete(dish){
                onSuccess()
            }
        }
    }
}