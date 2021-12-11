package com.shakir.spliff.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.shakir.spliff.data.model.UserResponse
import com.shakir.spliff.data.network.ApiInterface
import com.shakir.spliff.data.network.RetrofitClient
import com.shakir.spliff.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var code : String? = ""
    private var token : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

            loginUser(email,pass)
        }

        setContentView(binding.root)
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
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@LoginActivity,"User is not registered", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("error",t.localizedMessage)
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}