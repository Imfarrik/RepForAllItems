package com.example.nasa.network

import com.example.nasa.domain.Constants
import com.example.nasa.model.ApiNasaCuriosity
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface NasaApi {

    @GET(Constants.CURIOSITY_PHOTO_PATH)
    fun getPhotosCuriosity(
        @Query("sol") sol: Int = Constants.SOL,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Single<ApiNasaCuriosity>

    @GET(Constants.CURIOSITY_PHOTO_PATH)
    suspend fun getPhotosCuriosityPaging(
        @Query("sol") sol: Int = Constants.SOL,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int
    ): Response<ApiNasaCuriosity>

}