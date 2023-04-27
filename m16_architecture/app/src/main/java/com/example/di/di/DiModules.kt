package com.example.di.di

import com.example.di.domain.Api
import com.example.di.domain.Constants
import com.example.di.domain.GetUsefulActivityUseCase
import com.example.di.model.data.UsefulActivitiesRepository
import com.example.di.ui.main.MainViewModel
import com.example.di.ui.main.MainViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class AppModule() {

    @Provides
    fun provideUsefulActivitiesRepository(retrofitInstance: Api): UsefulActivitiesRepository {
        return UsefulActivitiesRepository(retrofitInstance)
    }

    @Provides
    fun provideGetUsefulActivityUseCase(repository: UsefulActivitiesRepository): GetUsefulActivityUseCase {
        return GetUsefulActivityUseCase(repository)
    }

    @Provides
    fun provideMainViewModel(getUsefulActivityUseCase: GetUsefulActivityUseCase): MainViewModel {
        return MainViewModel(getUsefulActivityUseCase)
    }

    @Provides
    fun provideMainViewModelFactory(mainViewModel: MainViewModel): MainViewModelFactory {
        return MainViewModelFactory(mainViewModel)
    }

    @Provides
    fun providerBankApi(okHttpClient: OkHttpClient): Api {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(Api::class.java)
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
