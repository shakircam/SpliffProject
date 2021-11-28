package com.shakir.spliff.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.shakir.spliff.R
import com.shakir.spliff.databinding.FragmentFlowersBinding
import com.shakir.spliff.databinding.FragmentSplashBinding
import com.shakir.spliff.ui.activity.ListActivity


class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        Handler().postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToBoarderFragment()
            view?.findNavController()?.navigate(action)
        },3000)
        return binding.root
    }


}