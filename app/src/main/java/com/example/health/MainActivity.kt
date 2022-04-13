package com.example.health

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.health.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val dataModel: DataModel by viewModels()
    var pref: SharedPreferences? = null

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

        //сохранение данных
        pref = getSharedPreferences("TABLE", MODE_PRIVATE)
        //
        dataModel.weight.value = pref?.getString(PreferencesConstants.WEIGHT,null)
        dataModel.height.value = pref?.getString(PreferencesConstants.HEIGHT,null)
        dataModel.age.value = pref?.getString(PreferencesConstants.AGE,null)
        Log.d("mainLog","log: ${dataModel.sex.value}")
        dataModel.sex.value = pref?.getBoolean(PreferencesConstants.SEX,false)

        dataModel.weight.observe(this) {
            prefSaveDataString(PreferencesConstants.WEIGHT,it)
        }
        dataModel.height.observe(this){
            prefSaveDataString(PreferencesConstants.HEIGHT,it)
        }
        dataModel.age.observe(this)
        {
            prefSaveDataString(PreferencesConstants.AGE,it)
        }
        dataModel.sex.observe(this)
        {
            prefSaveDataBoolean(PreferencesConstants.SEX,it)
        }
    }
    private fun showNewFragment(fragment: Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,fragment)
            .commit()
    }
    private fun prefSaveDataString(key: String, data: String?)
    {
        val editor = pref?.edit()
        editor?.putString(key,data)
        editor?.apply()
    }
    private fun prefSaveDataBoolean(key: String, data: Boolean)
    {
        val editor = pref?.edit()
        editor?.putBoolean(key,data)
        editor?.apply()
    }
}