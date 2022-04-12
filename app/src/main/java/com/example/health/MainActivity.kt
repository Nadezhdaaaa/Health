package com.example.health

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.health.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.selectedItemId = R.id.homeButton
        showNewFragment(HomeFragment.newInstance())
        //переключение между фрагментами в меню
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeButton -> {
                    showNewFragment(HomeFragment.newInstance())
                }
                R.id.vitaminsButton -> {
                    showNewFragment(VitaminsFragment.newInstance())
                }
                R.id.foodButton -> {
                    showNewFragment(FoodFragment.newInstance())
                }
                R.id.profileButton -> {
                    showNewFragment(ProfileFragment.newInstance())
                }
            }
            true
        }
    }
    fun showNewFragment(fragment: Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,fragment)
            .commit()
    }
}