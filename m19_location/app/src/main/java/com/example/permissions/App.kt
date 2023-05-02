package com.example.permissions

import android.app.Application
import com.example.permissions.di.AppComponent
import com.example.permissions.di.AppModule
import com.example.permissions.di.DaggerAppComponent
import com.example.permissions.ui.helper.Constants
import com.yandex.mapkit.MapKitFactory

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(Constants.MAP_KIT_TOKEN)
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}