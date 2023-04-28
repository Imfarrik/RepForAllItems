package com.example.nasa.di

import android.content.Context
import com.example.nasa.domain.Constants
import com.example.nasa.network.NasaApi
import com.example.nasa.network.NasaApiImpl
import com.example.nasa.network.ServiceApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class AppModule {

    @Provides
    fun provideNasaApiImpl(nasaApi: NasaApi): ServiceApi {
        return NasaApiImpl(nasaApi)
    }

    @Provides
    fun providerBankApi(okHttpClient: OkHttpClient): NasaApi {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.NASA_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(NasaApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS)
            .callTimeout(60000, TimeUnit.MILLISECONDS)
            .build()
    }
}