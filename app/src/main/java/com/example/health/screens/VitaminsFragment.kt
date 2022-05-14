package com.example.health.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.health.databinding.FragmentVitaminsBinding
import com.example.health.models.DataViewModel
import com.example.health.utilits.*
import java.text.SimpleDateFormat
import java.util.*

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
        //подгрузка прогресса
        val lastDate = APP_PREFERENCES.getString(DATE, "")
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val currentDate = dateFormat.format(Calendar.getInstance().time)
        if (!lastDate.isNullOrBlank() && lastDate == currentDate) {
            val nowKcal = APP_PREFERENCES.getString(KCAL_NOW,"")!!
            val nowFe = APP_PREFERENCES.getString(FE_NOW,"")
            val nowVD = APP_PREFERENCES.getString(VD_NOW,"")
            val nowVB12 = APP_PREFERENCES.getString(VB12_NOW,"")
            val nowOmega3 = APP_PREFERENCES.getString(OMEGA3_NOW,"")
            mBinding.kcalProgressBar.progress = nowKcal.toInt()
            when {
                !nowFe.isNullOrBlank() -> {
                    mBinding.feProgressBar.progress = nowFe.toDouble().toInt()
                    mBinding.nowFeTextView.text = nowFe
                }
                !nowVD.isNullOrBlank() -> {
                    mBinding.vDProgressBar.progress = nowVD.toDouble().toInt()
                    mBinding.nowVDTextView.text = nowVD
                }
                !nowVB12.isNullOrBlank() -> {
                    mBinding.vB12ProgressBar.progress = nowVB12.toDouble().toInt()
                    mBinding.nowVB12TextView.text = nowVB12
                }
                !nowOmega3.isNullOrBlank() -> {
                    mBinding.omega3ProgressBar.progress = nowOmega3.toDouble().toInt()
                    mBinding.nowOmega3TextView.text = nowOmega3
                }
            }
        }
        //обработка изменений прогресса
        dataViewModel.eatenFoodVitaminFrag.observe(viewLifecycleOwner) {
            if (dataViewModel.eatenFoodVitaminFrag.value != null) {
                for (amountDish in dataViewModel.eatenFoodVitaminFrag.value!!) {
                    mBinding.kcalProgressBar.progress += amountDish.dish.kcal * amountDish.amount / 100 * 100
                    val nowKcal = mBinding.nowKcalTextView.text.toString().toInt() + amountDish.dish.kcal * amountDish.amount / 100
                    mBinding.nowKcalTextView.text = nowKcal.toString()
                    APP_PREFERENCES.edit().putString(KCAL_NOW,nowKcal.toString()).apply()
                    if (amountDish.dish.fe != null) {
                        mBinding.feProgressBar.progress += (amountDish.dish.fe * amountDish.amount).toInt()
                        val nowFe = mBinding.nowFeTextView.text.toString().toDouble() + amountDish.dish.fe * amountDish.amount / 100
                        mBinding.nowFeTextView.text = nowFe.toString()
                        APP_PREFERENCES.edit().putString(FE_NOW,nowFe.toString()).apply()
                    }
                    if (amountDish.dish.vitaminD != null) {
                        mBinding.vDProgressBar.progress += (amountDish.dish.vitaminD * amountDish.amount).toInt()
                        val nowVD = mBinding.nowVDTextView.text.toString().toDouble() + amountDish.dish.vitaminD * amountDish.amount / 100
                        mBinding.nowVDTextView.text = nowVD.toString()
                        APP_PREFERENCES.edit().putString(VD_NOW,nowVD.toString()).apply()
                    }
                    if (amountDish.dish.vitaminB12 != null) {
                        mBinding.vB12ProgressBar.progress += (amountDish.dish.vitaminB12 * amountDish.amount).toInt()
                        val nowVB12 = mBinding.nowVB12TextView.text.toString().toDouble() + amountDish.dish.vitaminB12 * amountDish.amount / 100
                        mBinding.nowVB12TextView.text = nowVB12.toString()
                        APP_PREFERENCES.edit().putString(VB12_NOW,nowVB12.toString()).apply()
                    }
                    if (amountDish.dish.omega3 != null) {
                        mBinding.omega3ProgressBar.progress += (amountDish.dish.omega3 * amountDish.amount).toInt()
                        val nowOmega3 = mBinding.nowOmega3TextView.text.toString().toDouble() + amountDish.dish.omega3 * amountDish.amount / 100
                        mBinding.nowOmega3TextView.text = nowOmega3.toString()
                        APP_PREFERENCES.edit().putString(OMEGA3_NOW,nowOmega3.toString()).apply()
                    }
                }
                APP_PREFERENCES.edit().putString(DATE,currentDate).apply()
                dataViewModel.eatenFoodVitaminFrag.value = null
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