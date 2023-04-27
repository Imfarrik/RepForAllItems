package com.example.di.domain

import com.example.di.model.data.UsefulActivityDto
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("activity/")
    suspend fun getUser(): Response<UsefulActivityDto>

}
