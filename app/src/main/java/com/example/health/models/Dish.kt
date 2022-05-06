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
    val kcal: Int
)