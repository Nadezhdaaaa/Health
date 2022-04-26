package com.example.health.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.health.R
import com.example.health.databinding.FragmentHomeBinding
import com.example.health.utilits.APP_ACTIVITY

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mBinding.btnAddFood.setOnClickListener {
                Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_foodFragment)
            //APP_ACTIVITY.navController.navigate(R.id.action_homeFragment_to_foodFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}