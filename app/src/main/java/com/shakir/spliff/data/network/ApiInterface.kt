package com.shakir.spliff.data.network

import com.shakir.spliff.data.model.Registration
import com.shakir.spliff.data.model.User
import com.shakir.spliff.data.model.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @FormUrlEncoded
    @POST("api/v1/login")
     fun login(@Field("email")email:String,@Field("password")password: String ): Call<UserResponse>

    @FormUrlEncoded
    @POST("api/v1/register")
      fun registration(@Field("email") email: String,@Field("password") password: String,
                       @Field("c_password") c_password: String,@Field("name") name: String): Call<Registration>
}