package com.shakir.spliff.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.shakir.spliff.R
import com.shakir.spliff.databinding.FragmentBoarderBinding
import com.shakir.spliff.databinding.FragmentSplashBinding


class BoarderFragment : Fragment() {
    private var _binding : FragmentBoarderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBoarderBinding.inflate(inflater, container, false)

        binding.join.setOnClickListener {
            //Go to Registration
            val action = BoarderFragmentDirections.actionBoarderFragmentToRegistrationFragment()
            view?.findNavController()?.navigate(action)
        }

        binding.button1.setOnClickListener {
            //Go to Login
            val action = BoarderFragmentDirections.actionBoarderFragmentToLoginFragment()
            view?.findNavController()?.navigate(action)
        }
        return binding.root
    }


}