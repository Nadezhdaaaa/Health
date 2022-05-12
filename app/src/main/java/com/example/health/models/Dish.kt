package com.example.health.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "food_table",
    indices = [
        Index("name", unique = true)
    ]
)
data class Dish(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val kcal: Int,
    val fe: Double?,
    @ColumnInfo(name = "vitamin_d") val vitaminD: Double?,
    @ColumnInfo(name = "vitamin_b_12") val vitaminB12: Double?,
    @ColumnInfo(name = "omega_3") val omega3: Double?
)