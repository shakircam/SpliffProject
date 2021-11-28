package com.shakir.spliff.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.shakir.spliff.data.repository.UserRepository
import com.shakir.spliff.data.viewmodel.UserViewModel
import com.shakir.spliff.data.viewmodel.UserViewModelFactory
import com.shakir.spliff.databinding.FragmentLoginBinding
import com.shakir.spliff.ui.activity.HomeActivity



class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val repository by lazy { UserRepository() }
    private val viewModel by lazy {
        val factory = UserViewModelFactory(repository)
        ViewModelProvider(this, factory).get(UserViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener {

            val email = binding.email.editText?.text.toString()
            val pass = binding.outlinedPassword.editText?.text.toString()

            when{
                TextUtils.isEmpty(email) ->{
                    binding.email.error = "Email is required"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(pass) -> {
                    binding.outlinedPassword.error = "Password is Required"
                    return@setOnClickListener
                }
                pass.length<4 -> {
                    binding.outlinedPassword.error = "Password is less then four digit"
                    return@setOnClickListener
                }
            }

            viewModel.createUser(email,pass)
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
            }

        return binding.root
    }


}
