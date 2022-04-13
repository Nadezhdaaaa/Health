package com.example.health

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.health.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    var weight: String? = null
    var height: String? = null
    var age: String? = null
    var sex: Boolean = false
    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
        //pref = this.activity?.getSharedPreferences("TABLE", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weightEditText.setText(dataModel.weight.value)
        //сохранение данных профиля
        binding.saveButton.setOnClickListener {
            if(binding.weightEditText.text != null && binding.heightEditText.text != null && binding.ageEditText.text != null)
            {
                weight = binding.weightEditText.text.toString()
                height = binding.heightEditText.text.toString()
                age = binding.ageEditText.text.toString()
                sex = binding.sexSwitch.isChecked
                //сохранение в память
                dataModel.weight.value = weight
            }
            else{
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(context, "$weight | $height | $age | $age", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        fun newInstance() = ProfileFragment()
    }


}