package com.example.permissions.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PhotoModel(
    var uri: String,
    var date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
