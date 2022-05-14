package com.example.health.screens.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.health.R
import com.example.health.databinding.AddWaterDialogBinding
import com.example.health.databinding.FragmentHomeBinding
import com.example.health.models.DataViewModel
import com.example.health.utilits.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: HomeFragmentViewModel
    private val dataViewModel: DataViewModel by activityViewModels()

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
        mViewModel.initDatabase { }

        val weight = APP_PREFERENCES.getString(WEIGHT_PREFS, "")
        val age = APP_PREFERENCES.getString(AGE_PREFS, "")
        val sex = APP_PREFERENCES.getBoolean(SEX_PREFS, false)
        maxKcalCalculation(weight, age, sex)
        maxWaterCalculation(weight)

        //подгрузка прогресса
        val lastDate = APP_PREFERENCES.getString(HOME_DATE, "")
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val currentDate = dateFormat.format(Calendar.getInstance().time)
        if (!lastDate.isNullOrBlank() && lastDate == currentDate){
            val nowKcal = APP_PREFERENCES.getString(HOME_KCAL,"")!!
            mBinding.kkalProgressBar.progress = nowKcal.toInt()
        }
        val lastDateWater = APP_PREFERENCES.getString(WATER_DATE,"")
        if (!lastDateWater.isNullOrBlank() && lastDateWater == currentDate) {
            val nowWater = APP_PREFERENCES.getString(WATER_NOW,"")!!
            mBinding.waterProgressBar.progress = nowWater.toInt()
        }
        //обработка изменений
        dataViewModel.eatenFoodHomeFrag.observe(viewLifecycleOwner) {
            if (dataViewModel.eatenFoodHomeFrag.value != null) {
                for (amountDish in dataViewModel.eatenFoodHomeFrag.value!!) {
                    val nowKcal = mBinding.kkalProgressBar.progress + amountDish.dish.kcal * amountDish.amount
                    mBinding.kkalProgressBar.progress = nowKcal
                    //val nowKcal = mBinding.nowKcalTextView.text.toString().toInt() + amountDish.dish.kcal * amountDish.amount / 100
                    //mBinding.nowKcalTextView.text = nowKcal.toString()
                    APP_PREFERENCES.edit().putString(HOME_KCAL,nowKcal.toString()).apply()
                }
                APP_PREFERENCES.edit().putString(HOME_DATE,currentDate).apply()
                dataViewModel.eatenFoodHomeFrag.value = null
            }
        }

        mBinding.btnAddWater.setOnClickListener{
            addWaterClick(currentDate)
        }
    }

    private fun maxKcalCalculation(weight: String?, age: String?, sex: Boolean) {
        if (weight.isNullOrBlank() || age.isNullOrBlank()) return
        val kcalMax: Int
        //mBinding.noDataKcalTextView.visibility = View.GONE
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
        mBinding.kkalProgressBar.max = kcalMax * 100
    }

    private fun maxWaterCalculation(weight: String?){
        if (weight.isNullOrBlank()) return
        val waterMax = weight.toInt() * 37.5
        mBinding.waterProgressBar.max = waterMax.toInt()
    }

    private fun addWaterClick(currentDate: String){
        val dialogBinding = AddWaterDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.water)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.confirm, null)
            .create()

        dialog.setOnShowListener {
            dialogBinding.waterValueSeekbar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        dialogBinding.waterValueEditText.setText(progress.toString())
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
            dialogBinding.waterValueEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(textField: Editable?) {
                    if (textField.toString() != "") {
                        dialogBinding.waterValueSeekbar.progress = textField.toString().toInt()
                    }
                }
            })
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener positiveClick@{
                val edittext = dialogBinding.waterValueEditText.text.toString()
                if (edittext.isBlank()) {
                    dialogBinding.waterValueEditText.error = context?.getString(R.string.enter_value)
                    return@positiveClick
                }
                val waterNow = mBinding.waterProgressBar.progress + edittext.toInt()
                mBinding.waterProgressBar.progress = waterNow
                APP_PREFERENCES.edit().putString(WATER_NOW,waterNow.toString()).apply()
                APP_PREFERENCES.edit().putString(WATER_DATE,currentDate).apply()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}