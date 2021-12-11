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
import com.shakir.spliff.data.model.UserResponse
import com.shakir.spliff.data.network.ApiInterface
import com.shakir.spliff.data.network.RetrofitClient
import com.shakir.spliff.data.repository.UserRepository
import com.shakir.spliff.data.viewmodel.UserViewModel
import com.shakir.spliff.data.viewmodel.UserViewModelFactory
import com.shakir.spliff.databinding.FragmentLoginBinding
import com.shakir.spliff.ui.activity.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var code : String? = ""
    private var token : String? = ""

    private val repository by lazy { UserRepository() }
    private val viewModel by lazy {
        val factory = UserViewModelFactory(repository)
        ViewModelProvider(this, factory)[UserViewModel::class.java]
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

           // viewModel.createUser(email,pass)
            loginUser(email,pass)


        }

        return binding.root
    }

    private fun loginUser(email:String, password:String){

        val apiInterface = RetrofitClient.getClient().create(ApiInterface::class.java)
        val call =  apiInterface.login(email, password)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                response.body().let {
                     token =  it?.success?.token.toString()
                    Log.d("token", token!!)
                    code = response.code().toString()

                    if (code == "200" ){
                        Log.d("co", code!!)
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(requireContext(),"User is not registered",Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("error",t.localizedMessage)
            }

        })
    }

}
