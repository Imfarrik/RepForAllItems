package com.example.nasa.network

import com.example.nasa.model.ApiNasaCuriosity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class NasaApiImpl(private val nasaApi: NasaApi) : ServiceApi {
    override fun getPhotosCuriosity(): Single<ApiNasaCuriosity> {
        return nasaApi.getPhotosCuriosity().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun getPhotosCuriosityPaging(page: Int): Response<ApiNasaCuriosity> {
        return nasaApi.getPhotosCuriosityPaging(page = page)
    }


}