package com.shakir.spliff.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.shakir.spliff.R
import com.shakir.spliff.databinding.FragmentAddToBinding
import com.shakir.spliff.databinding.FragmentFlowersBinding


class AddToFragment : DialogFragment() {
    private var _binding : FragmentAddToBinding? = null
    private val binding get() = _binding!!
    var number = 1
    var counter = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddToBinding.inflate(inflater, container, false)

        binding.addButton.setOnClickListener {
            if (counter<9){
                counter ++
            }
            number = counter * 1
            binding.number.text = number.toString()
        }

        binding.minusButton.setOnClickListener {
            if (counter>1){
                counter--
            }
            number = counter / 1
            binding.number.text = number.toString()

        }

        binding.cart.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}