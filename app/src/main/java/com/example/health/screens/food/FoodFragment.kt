package com.example.health.screens.food

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.databinding.AddFoodDialogBinding
import com.example.health.databinding.AddNewFoodDialogBinding
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
                    dialogBinding.foodNameEditText.error = (R.string.enter_value).toString()
                    return@setOnClickListener
                }
                if(kcal.isBlank()) {
                    dialogBinding.foodKcalEditText.error = (R.string.enter_value).toString()
                    return@setOnClickListener
                }
                mViewModel.insert(Dish(id = 0,
                    name = name,
                    kcal = kcal.toInt(),
                    fe = fe.toIntOrNull(),
                    vitaminD = vitaminD.toIntOrNull(),
                    vitaminB12 = vitaminB12.toIntOrNull(),
                    omega3 = omega3.toIntOrNull())){
                    dialog.dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewModel.allDishes.removeObserver(mObserverList)
        mRecyclerView.adapter = null
    }

    companion object{
        fun click(foodItem: Dish){
            showAddFoodAlertDialog(foodItem)
        }

        private fun showAddFoodAlertDialog(foodItem: Dish) {
            val layoutInflater = LayoutInflater.from(APP_ACTIVITY)
            val dialogBinding = AddFoodDialogBinding.inflate(layoutInflater)

            val mContext = APP_ACTIVITY
            val dialog = AlertDialog.Builder(mContext)
                .setTitle(foodItem.name)
                .setView(dialogBinding.root)
                .setPositiveButton(mContext.getString(R.string.confirm),null)
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
                        dialogBinding.foodValueEditText.error = mContext.getString(R.string.enter_value)
                        return@setOnClickListener
                    }
                    //todo save values
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }

}