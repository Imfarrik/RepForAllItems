package com.example.permissions.di

import android.content.Context
import androidx.room.Room
import com.example.permissions.model.network.OpenTripApi
import com.example.permissions.model.network.OpenTripImpl
import com.example.permissions.model.network.ServiceApi
import com.example.permissions.room.AppDatabase
import com.example.permissions.ui.helper.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun providerAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "photo_database")
            .build()
    }

    @Provides
    fun provideOpenTripApiImpl(openTripApi: OpenTripApi): ServiceApi {
        return OpenTripImpl(openTripApi)
    }

    @Provides
    fun providerApi(okHttpClient: OkHttpClient): OpenTripApi {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.OPEN_TRIP_HTTP)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(OpenTripApi::class.java)
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


