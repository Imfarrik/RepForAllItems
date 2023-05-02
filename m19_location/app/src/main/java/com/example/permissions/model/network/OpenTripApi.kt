package com.example.permissions.model.network

import com.example.permissions.model.data.apiAll.ApiAll
import com.example.permissions.model.data.apiGetCountry.ApiGetCountry
import com.example.permissions.ui.helper.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface OpenTripApi {

    @GET("ru/places/geoname")
    fun getPlaces(
        @Query("name") name: String,
        @Query("apikey") apiKey: String = Constants.OPEN_TRIP_TOKEN
    ): Observable<ApiGetCountry>

    @GET("ru/places/radius")
    fun getAll(
        @Query("radius") name: Int = 1000,
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("apikey") apiKey: String = Constants.OPEN_TRIP_TOKEN
    ): Observable<ApiAll>

}