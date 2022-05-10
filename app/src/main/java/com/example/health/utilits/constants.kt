package com.example.health.utilits

import android.content.SharedPreferences
import com.example.health.MainActivity
import com.example.health.database.DataBaseRepository

lateinit var APP_ACTIVITY: MainActivity
lateinit var REPOSITORY: DataBaseRepository

// SharedPrefs constants
lateinit var APP_PREFERENCES: SharedPreferences
const val APP_PREFERENCES_NAME = "APP_PREFERENCES"
const val WEIGHT_PREFS = "WEIGHT_PREFS"
const val HEIGHT_PREFS = "HEIGHT_PREFS"
const val AGE_PREFS = "AGE_PREFS"
const val SEX_PREFS = "SEX_PREFS"