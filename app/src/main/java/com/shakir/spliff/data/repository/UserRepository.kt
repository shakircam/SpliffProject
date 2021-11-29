package com.shakir.spliff.data.repository

import android.content.Context
import android.util.Log
import com.shakir.spliff.data.model.Registration
import com.shakir.spliff.data.model.UserResponse
import com.shakir.spliff.data.network.ApiInterface
import com.shakir.spliff.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository() {


    suspend fun loginUser(email:String,password:String){

          val apiInterface = RetrofitClient.getClient().create(ApiInterface::class.java)
       // return apiInterface.login(email, password)
        /*  val call =  apiInterface.login(email, password)
          call.enqueue(object : Callback<UserResponse> {
             override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                response.body().let {
                  val token =  it?.success?.token.toString()
                    Log.d("token",token)

                }
             }

             override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                 Log.d("error",t.localizedMessage)
             }

         })*/
    }

    fun doRegistration(email:String,password:String,c_password:String,name:String){
        val apiInterface = RetrofitClient.getClient().create(ApiInterface::class.java)

        val call = apiInterface.registration(email, password, c_password,name)
        call.enqueue(object : Callback<Registration> {
            override fun onResponse(call: Call<Registration>, response: Response<Registration>) {
                response.body().let {
                    val token= it?.success?.token
                    Log.d("reg",token.toString())
                }
            }

            override fun onFailure(call: Call<Registration>, t: Throwable) {
                Log.d("error",t.localizedMessage)
            }

        })

    }
}