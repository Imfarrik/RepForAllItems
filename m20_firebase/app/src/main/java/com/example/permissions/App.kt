package com.example.permissions

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.permissions.di.AppComponent
import com.example.permissions.di.AppModule
import com.example.permissions.di.DaggerAppComponent
import com.example.permissions.ui.helper.Constants
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.yandex.mapkit.MapKitFactory

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent = appComponent
        const val NOTIFICATION_CHANNEL_ID = "test"
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(Constants.MAP_KIT_TOKEN)
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChanel()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChanel() {

        val name = "Test notification channel"
        val descriptionTxt = "Simple description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = descriptionTxt
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


}