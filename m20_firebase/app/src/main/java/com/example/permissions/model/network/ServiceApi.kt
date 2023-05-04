package com.example.permissions.model.network

import com.example.permissions.model.data.apiAll.ApiAll
import com.example.permissions.model.data.apiGetCountry.ApiGetCountry
import io.reactivex.rxjava3.core.Observable

interface ServiceApi {

    fun getPlaces(country: String): Observable<ApiGetCountry>
    fun getAll(
        lon: Double, lat: Double
    ): Observable<ApiAll>

}