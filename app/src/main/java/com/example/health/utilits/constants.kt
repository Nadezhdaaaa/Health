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

const val DATE = "DATE_PREFS"
const val KCAL_NOW = "KCAL_NOW"
const val FE_NOW = "FE_NOW"
const val VD_NOW = "VD_NOW"
const val VB12_NOW = "VB12_NOW"
const val OMEGA3_NOW = "OMEGA3_NOW"
const val WATER_DATE = "WATER_DATE"
const val WATER_NOW = "WATER_NOW"
const val HOME_DATE = "HOME_DATE"
const val HOME_KCAL = "HOME_KCAL"