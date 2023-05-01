package com.example.permissions

import android.app.Application
import com.example.permissions.di.AppComponent
import com.example.permissions.di.AppModule
import com.example.permissions.di.DaggerAppComponent

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}