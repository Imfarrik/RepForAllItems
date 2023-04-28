package com.example.nasa.network

import com.example.nasa.model.ApiNasaCuriosity
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface ServiceApi {

    fun getPhotosCuriosity(): Single<ApiNasaCuriosity>
    suspend fun getPhotosCuriosityPaging(page: Int): Response<ApiNasaCuriosity>

}