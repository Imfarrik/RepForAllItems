package com.example.permissions.di

import android.content.Context
import androidx.room.Room
import com.example.permissions.room.AppDatabase
import dagger.Module
import dagger.Provides

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

}


