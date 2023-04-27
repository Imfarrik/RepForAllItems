package com.example.di.model.data

import com.example.di.domain.Api
import com.example.di.model.entity.UsefulActivity
import javax.inject.Inject

class UsefulActivitiesRepository @Inject constructor(private val apiService: Api) {

    suspend fun getUsefulActivity(): UsefulActivity {

        val response = apiService.getUser()
        if (response.isSuccessful) {
            val usefulActivityDto = response.body()
            if (usefulActivityDto != null) {
                return usefulActivityDto
            }
        }
        throw Exception("Failed to get useful activity")
    }
}