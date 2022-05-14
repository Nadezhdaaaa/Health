package com.example.health.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.health.R
import com.example.health.databinding.FragmentVitaminsBinding
import com.example.health.models.DataViewModel
import com.example.health.utilits.*

class VitaminsFragment : Fragment() {

    private var _binding: FragmentVitaminsBinding? = null
    private val mBinding get() = _binding!!
    private val dataViewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVitaminsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        val weight = APP_PREFERENCES.getString(WEIGHT_PREFS, "")
        val age = APP_PREFERENCES.getString(AGE_PREFS, "")
        val sex = APP_PREFERENCES.getBoolean(SEX_PREFS, false)
        maxKcalCalculation(weight, age, sex)
        maxFeCalculation(age, sex)
        maxVDCalculation(age)
        maxB12Calculation(age)
        dataViewModel.eatenFoodVitaminFrag.observe(viewLifecycleOwner) {
            if(dataViewModel.eatenFoodVitaminFrag.value != null) {
                for (amountDish in dataViewModel.eatenFoodVitaminFrag.value!!) {
                    mBinding.kcalProgressBar.progress += amountDish.dish.kcal * amountDish.amount / 100 * 100
                    dataViewModel.eatenFoodVitaminFrag.value = null
                }
            }
        }
    }

    private fun maxKcalCalculation(weight: String?, age: String?, sex: Boolean) {
        if (weight.isNullOrBlank() || age.isNullOrBlank()) return
        val kcalMax: Int
        mBinding.noDataKcalTextView.visibility = View.GONE
        if (!sex) {
            kcalMax = if (age.toInt() < 31) {
                ((weight.toInt() * 0.063 + 2.896) * 240 * 1.3).toInt()
            } else {
                if (age.toInt() < 61) {
                    ((weight.toInt() * 0.0484 + 3.653) * 240 * 1.3).toInt()
                } else {
                    ((weight.toInt() * 0.0491 + 2.459) * 240 * 1.3).toInt()
                }
            }
        } else {
            kcalMax = if (age.toInt() < 31) {
                ((weight.toInt() * 0.062 + 2.036) * 240 * 1.3).toInt()
            } else {
                if (age.toInt() < 61) {
                    ((weight.toInt() * 0.034 + 3.538) * 240 * 1.3).toInt()
                } else {
                    ((weight.toInt() * 0.038 + 2.755) * 240 * 1.3).toInt()
                }
            }
        }
        mBinding.maxKcalTextView.text = kcalMax.toString()
        mBinding.kcalProgressBar.max = kcalMax * 100
    }

    private fun maxFeCalculation(age: String?, sex: Boolean) {
        if (age.isNullOrBlank()) return
        mBinding.noDataFeTextView.visibility = View.GONE
        val feMax: Int = if (!sex) (if (age.toInt() in 14..18) 11 else 8)
        else if (age.toInt() in 14..18)
            15
        else if (age.toInt() in 19..50)
            18
        else 8
        mBinding.maxFeTextView.text = feMax.toString()
        mBinding.feProgressBar.max = feMax * 100
    }

    private fun maxVDCalculation(age: String?) {
        if (age.isNullOrBlank()) return
        mBinding.noDataVDTextView.visibility = View.GONE
        val vDMax: Int = if (age.toInt() < 71) 15 else 20
        mBinding.maxVDTextView.text = vDMax.toString()
        mBinding.vDProgressBar.max = vDMax * 100
    }

    private fun maxB12Calculation(age: String?) {
        if (age.isNullOrBlank()) return
        mBinding.noDataVB12TextView.visibility = View.GONE
        val vB12Max: Double = if (age.toInt() < 14) 1.8 else 2.4
        mBinding.maxVB12TextView.text = vB12Max.toString()
        mBinding.vB12ProgressBar.max = (vB12Max * 100).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}