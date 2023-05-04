package com.example.permissions.model.network

import com.example.permissions.model.data.apiAll.ApiAll
import com.example.permissions.model.data.apiGetCountry.ApiGetCountry
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class OpenTripImpl(private val api: OpenTripApi) : ServiceApi {

    override fun getPlaces(country: String): Observable<ApiGetCountry> {
        return api.getPlaces(country).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAll(lon: Double, lat: Double): Observable<ApiAll> {
        return api.getAll(lon = lon, lat = lat).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}