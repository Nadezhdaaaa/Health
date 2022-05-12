package com.example.health.screens.food

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.databinding.AddFoodDialogBinding
import com.example.health.databinding.AddNewFoodDialogBinding
import com.example.health.databinding.FoodInfoDialogBinding
import com.example.health.databinding.FragmentFoodBinding
import com.example.health.models.Dish
import com.example.health.utilits.APP_ACTIVITY
import com.example.health.utilits.APP_PREFERENCES
import com.example.health.utilits.WEIGHT_PREFS

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
    ): View {
        _binding = FragmentFoodBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mAdapter = FoodAdapter(object:FoodActionListener{
            override fun onFoodAdd(dish: Dish) {
                showAddFoodAlertDialog(dish)
            }
            override fun onFoodInfoDelete(dish: Dish) {
                showInfoFoodAlertDialog(dish)
            }
        })
        mRecyclerView = mBinding.foodRecyclerView
        mRecyclerView.adapter = mAdapter
        mObserverList = Observer {
            val list = it//.sortedBy { it.name }
            mAdapter.setList(list)
        }
        mViewModel = ViewModelProvider(this).get(FoodFragmentViewModel::class.java)
        mViewModel.allDishes.observe(this,mObserverList)
        mBinding.btnAddNewFood.setOnClickListener {
            showAddNewFoodAlertDialog()
        }
    }

    private fun showAddNewFoodAlertDialog() {
        val dialogBinding = AddNewFoodDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.add_new_food)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.confirm,null)
            .create()
        dialog.setOnShowListener{
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
                val name = dialogBinding.foodNameEditText.text.toString()
                val kcal = dialogBinding.foodKcalEditText.text.toString()
                val fe = dialogBinding.foodFeEditText.text.toString()
                val vitaminD = dialogBinding.foodVDEditText.text.toString()
                val vitaminB12 = dialogBinding.foodVB12EditText.text.toString()
                val omega3 = dialogBinding.foodOmega3EditText.text.toString()
                if(name.isBlank()) {
                    dialogBinding.foodNameEditText.error = getString(R.string.enter_value)
                    return@setOnClickListener
                }
                if(kcal.isBlank()) {
                    dialogBinding.foodKcalEditText.error = getString(R.string.enter_value)
                    return@setOnClickListener
                }
                mViewModel.insert(Dish(id = 0,
                    name = name,
                    kcal = kcal.toInt(),
                    fe = fe.toDoubleOrNull(),
                    vitaminD = vitaminD.toDoubleOrNull(),
                    vitaminB12 = vitaminB12.toDoubleOrNull(),
                    omega3 = omega3.toDoubleOrNull())){
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun showInfoFoodAlertDialog(dish: Dish) {
        val dialogBinding = FoodInfoDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(context)
            .setTitle(dish.name)
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.Ok),null)
            .setNeutralButton(getString(R.string.Delete),null)
            .create()
        dialog.setOnShowListener {
            with(dialogBinding) {
                foodNameEditText.setText(dish.name)
                foodKcalEditText.setText(dish.kcal.toString())
                if (dish.fe == null) {
                    foodFeEditText.setText(getString(R.string.null_data))
                } else {
                    foodFeEditText.setText(dish.fe.toString())
                }
                if (dish.vitaminD == null) {
                    foodVDEditText.setText(getString(R.string.null_data))
                } else {
                    foodVDEditText.setText(dish.vitaminD.toString())
                }
                if (dish.vitaminB12 == null) {
                    foodVB12EditText.setText(getString(R.string.null_data))
                } else {
                    foodVB12EditText.setText(dish.vitaminB12.toString())
                }
                if (dish.omega3 == null) {
                    foodOmega3EditText.setText(getString(R.string.null_data))
                } else {
                    foodOmega3EditText.setText(dish.omega3.toString())
                }
                foodNameEditText.inputType = InputType.TYPE_NULL
                foodKcalEditText.inputType = InputType.TYPE_NULL
                foodFeEditText.inputType = InputType.TYPE_NULL
                foodVDEditText.inputType = InputType.TYPE_NULL
                foodVB12EditText.inputType = InputType.TYPE_NULL
            }
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                dialog.dismiss()
            }
            dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener {
                mViewModel.delete(dish){
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun showAddFoodAlertDialog(foodItem: Dish) {
        val dialogBinding = AddFoodDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(context)
            .setTitle(foodItem.name)
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.confirm),null)
            .create()
        dialog.setOnShowListener {
            dialogBinding.foodValueSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        dialogBinding.foodValueEditText.setText(progress.toString())
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            })
            dialogBinding.foodValueEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun afterTextChanged(textField: Editable?) {
                    if(textField.toString()!="") {
                        dialogBinding.foodValueSeekbar.progress = textField.toString().toInt()
                    }
                }
            })
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
                val edittext = dialogBinding.foodValueEditText.text.toString()
                if(edittext.isBlank()){
                    dialogBinding.foodValueEditText.error = getString(R.string.enter_value)
                    return@setOnClickListener
                }
                Toast.makeText(context, APP_PREFERENCES.getString(WEIGHT_PREFS,"kek"), Toast.LENGTH_SHORT).show()
                //todo save values
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.allDishes.removeObserver(mObserverList)
        mRecyclerView.adapter = null
    }
}