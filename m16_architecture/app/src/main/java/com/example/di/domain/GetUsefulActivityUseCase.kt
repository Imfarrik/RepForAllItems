package com.example.di.domain

import com.example.di.model.data.UsefulActivitiesRepository
import com.example.di.model.entity.UsefulActivity
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(private val repository: UsefulActivitiesRepository) {
    suspend fun execute(): UsefulActivity {
        return repository.getUsefulActivity()
    }
}