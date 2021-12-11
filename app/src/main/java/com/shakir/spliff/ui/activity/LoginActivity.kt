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
    //private var email : String? = ""
    //private var password : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val sharedPreferences = getSharedPreferences("my_sharedPreference",0)
        val editor = sharedPreferences.edit()
        val emil = sharedPreferences.getString("email",null)
        val pas = sharedPreferences.getString("pass",null)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if ( emil!= null && pas!= null){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.login.setOnClickListener {

            val email = binding.email.editText?.text.toString()
            val pass = binding.outlinedPassword.editText?.text.toString()

            editor.putString("email",email)
            editor.putString("pass",pass)
            editor.commit()

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
                    code = response.code().toString()

                    Log.d("token", token!!)

                    if (code == "200" ){
                        Log.d("co", code!!)
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
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


}