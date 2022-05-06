package com.example.health.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.health.database.room.AppDatabase
import com.example.health.database.room.FoodRoomRepository
import com.example.health.utilits.REPOSITORY

class HomeFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val mContext = application

    fun initDatabase(onSuccess:()->Unit){
        val dao = AppDatabase.getInstance(mContext).getFoodRoomDao()
        REPOSITORY = FoodRoomRepository(dao)
        onSuccess()
    }
}