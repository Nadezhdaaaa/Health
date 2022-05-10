package com.example.health

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.health.databinding.ActivityMainBinding
import com.example.health.utilits.APP_ACTIVITY
import com.example.health.utilits.APP_PREFERENCES
import com.example.health.utilits.APP_PREFERENCES_NAME

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this

        navController = findNavController(R.id.nav_host_fragment_container)
        val bottomNavigationView = mBinding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        APP_PREFERENCES = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}