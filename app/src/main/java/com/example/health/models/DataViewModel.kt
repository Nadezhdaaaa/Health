package com.example.health.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataViewModel : ViewModel() {
    val eatenFoodVitaminFrag: MutableLiveData<AmountDish> by lazy {
        MutableLiveData<AmountDish>()
    }
    val eatenFoodHomeFrag: MutableLiveData<Dish> by lazy {
        MutableLiveData<Dish>()
    }
}