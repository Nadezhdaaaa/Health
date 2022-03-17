package com.example.health

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.health.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.selectedItemId = R.id.homeButton

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeButton -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,HomeFragment.newInstance())
                }
                R.id.vitaminsButton -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,VitaminsFragment.newInstance())
                }
                R.id.foodButton -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,FoodFragment.newInstance())
                }
                R.id.profileButton -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout,ProfileFragment.newInstance())
                }
            }
            true
        }
    }
}