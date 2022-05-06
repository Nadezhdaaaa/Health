package com.example.health.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.health.R
import com.example.health.databinding.FragmentHomeBinding
import com.example.health.utilits.APP_ACTIVITY

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: HomeFragmentViewModel

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
        mViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        mViewModel.initDatabase {  }
        mBinding.btnAddFood.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_foodFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}