package com.example.health.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataViewModel : ViewModel() {
    val eatenFoodVitaminFrag: MutableLiveData<MutableList<AmountDish>> by lazy {
        MutableLiveData<MutableList<AmountDish>>()
    }
    val eatenFoodHomeFrag: MutableLiveData<MutableList<AmountDish>> by lazy {
        MutableLiveData<MutableList<AmountDish>>()
    }
}