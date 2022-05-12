package com.example.health.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.health.models.Dish

@Database(
    version = 2,
    entities = [
        Dish::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFoodRoomDao(): FoodRoomDao

    companion object{

        @Volatile
        private var database: AppDatabase?=null

        @Synchronized
        fun getInstance(context: Context): AppDatabase{
            database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "database.db"
                )
                .createFromAsset("initial_database.db")
                .build()
            return database as AppDatabase
        }
    }
}