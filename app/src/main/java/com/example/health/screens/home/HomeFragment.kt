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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.health.R
import com.example.health.databinding.AddWaterDialogBinding
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
        mViewModel.initDatabase { }
        mBinding.btnAddWater.setOnClickListener {
            addWaterClick()
        }
    }

    private fun addWaterClick(){
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
                //todo save values
                dialog.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}