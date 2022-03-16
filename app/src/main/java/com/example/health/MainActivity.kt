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

        binding.homeButton.setOnClickListener{
            supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,HomeFragment.newInstance())
            .commit()
        }
        binding.vitaminsButton.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout,VitaminsFragment.newInstance())
                .commit()
        }
        binding.foodButton.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout,FoodFragment.newInstance())
                .commit()
        }
        binding.profileButton.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout,ProfileFragment.newInstance())
                .commit()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,HomeFragment.newInstance())
            .commit()
    }
}