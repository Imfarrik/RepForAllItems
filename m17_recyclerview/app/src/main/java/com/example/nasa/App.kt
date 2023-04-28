package com.example.nasa

import android.app.Application
import com.example.nasa.di.AppComponent
import com.example.nasa.di.AppModule
import com.example.nasa.di.DaggerAppComponent


class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
    }

}