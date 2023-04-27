package com.example.di

import android.app.Application
import com.example.di.di.AppComponent
import com.example.di.di.DaggerAppComponent

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }

}