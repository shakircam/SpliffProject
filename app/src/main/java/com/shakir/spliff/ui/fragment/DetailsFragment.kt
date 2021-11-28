package com.shakir.spliff.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import com.shakir.spliff.R
import com.shakir.spliff.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        parentFragmentManager.setFragmentResultListener("dataFrom",this, FragmentResultListener { requestKey, result ->
            val data = result.get("name")
            binding.title.text = data.toString()
        } )


        return binding.root
    }


}