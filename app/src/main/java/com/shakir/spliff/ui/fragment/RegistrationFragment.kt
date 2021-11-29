package com.shakir.spliff.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.shakir.spliff.R
import com.shakir.spliff.data.model.Registration
import com.shakir.spliff.data.model.UserResponse
import com.shakir.spliff.data.network.ApiInterface
import com.shakir.spliff.data.network.RetrofitClient
import com.shakir.spliff.data.repository.UserRepository
import com.shakir.spliff.data.viewmodel.UserViewModel
import com.shakir.spliff.data.viewmodel.UserViewModelFactory
import com.shakir.spliff.databinding.FragmentBoarderBinding
import com.shakir.spliff.databinding.FragmentRegistrationBinding
import com.shakir.spliff.ui.activity.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    lateinit var userCheckBox: CheckBox
    private var code : String? = ""
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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        userCheckBox = binding.check

        binding.registration.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.outlinedPassword.editText?.text.toString()
            val cpass = binding.cPassword.editText?.text.toString()
            val name = binding.name.editText?.text.toString()

            when {
                TextUtils.isEmpty(name) -> {
                    binding.name.error = "Name is Required"
                    return@setOnClickListener
                }

                TextUtils.isEmpty(email) -> {
                    binding.email.error = "Email is required"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(password) -> {
                    binding.outlinedPassword.error = "Password is Required"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(cpass) -> {
                    binding.cPassword.error = "ConfirmPassword is Required"
                    return@setOnClickListener
                }

                password != cpass -> {
                    binding.cPassword.error = "Password is not match"
                    return@setOnClickListener
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.email.error = "Email isn't valid "
                    return@setOnClickListener
                }
                password.length < 4 -> {
                    binding.outlinedPassword.error = "Password is less then four digit"
                    return@setOnClickListener
                }

            }

            doRegistration(email,password,cpass,name)

        }

        binding.logText.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
            view?.findNavController()?.navigate(action)
        }

        return binding.root
    }

    fun doRegistration(email:String,password:String,c_password:String,name:String){
        val apiInterface = RetrofitClient.getClient().create(ApiInterface::class.java)

        val call = apiInterface.registration(email, password, c_password,name)
        call.enqueue(object : Callback<Registration> {
            override fun onResponse(call: Call<Registration>, response: Response<Registration>) {
                response.body().let {
                    val token= it?.success?.token
                    Log.d("reg",token.toString())
                    code = response.code().toString()

                   if (code == "500"){
                        Toast.makeText(requireContext(),"User already registered",Toast.LENGTH_SHORT).show()
                    }
                    else{
                       val intent = Intent(activity, HomeActivity::class.java)
                       startActivity(intent)

                   }
                }
            }

            override fun onFailure(call: Call<Registration>, t: Throwable) {
                Log.d("error",t.localizedMessage)
            }

        })

    }

}


