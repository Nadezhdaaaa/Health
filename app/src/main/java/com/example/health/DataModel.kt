package com.example.health

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val weight : MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }
}