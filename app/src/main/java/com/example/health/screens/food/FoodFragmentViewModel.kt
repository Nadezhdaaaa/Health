package com.example.health.screens.food

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.health.utilits.REPOSITORY

class FoodFragmentViewModel(application: Application): AndroidViewModel(application) {
    val allDishes = REPOSITORY.allDishes
}