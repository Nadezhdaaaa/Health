package com.example.health.screens.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.databinding.FragmentFoodBinding
import com.example.health.models.Dish
import com.example.health.utilits.APP_ACTIVITY

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: FoodFragmentViewModel
    private lateinit var mAdapter: FoodAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mObserverList: Observer<List<Dish>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mAdapter = FoodAdapter()
        mRecyclerView = mBinding.foodRecyclerView
        mRecyclerView.adapter = mAdapter
        mObserverList = Observer {
            val list = it//.sortedBy { it.name }
            mAdapter.setList(list)
        }
        mViewModel = ViewModelProvider(this).get(FoodFragmentViewModel::class.java)
        mViewModel.allDishes.observe(this,mObserverList)
        mBinding.btnAddNewFood.setOnClickListener {
            findNavController().navigate(R.id.action_foodFragment_to_addNewFoodFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.allDishes.removeObserver(mObserverList)
        mRecyclerView.adapter = null
    }


}