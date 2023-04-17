package com.example.restapi.ui.main.network

import com.example.restapi.ui.main.model.getUserApi.GetUserApi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val usersApi = retrofit.create(Api::class.java)

}

interface Api {

    @GET("/api/")
    suspend fun getUser(): Response<GetUserApi>

}