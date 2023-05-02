package com.example.permissions.room

import androidx.room.*
import com.example.permissions.model.PhotoModel

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo")
    fun getAll(): MutableList<PhotoModel>

    @Insert
    fun insert(vararg photoModel: PhotoModel)

    @Insert
    fun insert(photoModel: MutableList<PhotoModel>)

    @Delete
    fun delete(photoModel: PhotoModel)

}