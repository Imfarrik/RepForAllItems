package com.example.permissions.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.permissions.model.PhotoModel

@Database(entities = [PhotoModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}