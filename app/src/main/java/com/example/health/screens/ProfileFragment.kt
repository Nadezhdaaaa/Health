package com.example.health.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.health.databinding.FragmentProfileBinding
import com.example.health.utilits.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.weightEditText.setText(APP_PREFERENCES.getString(WEIGHT_PREFS, ""))
        mBinding.heightEditText.setText(APP_PREFERENCES.getString(HEIGHT_PREFS, ""))
        mBinding.ageEditText.setText(APP_PREFERENCES.getString(AGE_PREFS, ""))
        val sexIsChecked = APP_PREFERENCES.getBoolean(SEX_PREFS, false)
        mBinding.sexSwitch.isChecked = sexIsChecked
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mBinding.saveButton.setOnClickListener {
            val weightText = mBinding.weightEditText.text.toString()
            val heightText = mBinding.heightEditText.text.toString()
            val ageText = mBinding.ageEditText.text.toString()
            val sexValue = mBinding.sexSwitch.isChecked as Boolean
            APP_PREFERENCES.edit()
                .putString(WEIGHT_PREFS, weightText)
                .putString(HEIGHT_PREFS, heightText)
                .putString(AGE_PREFS, ageText)
                .putBoolean(SEX_PREFS,sexValue)
                .apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}