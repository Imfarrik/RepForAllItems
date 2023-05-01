package com.example.permissions.ui.fragment.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.permissions.App
import com.example.permissions.model.PhotoModel
import com.example.permissions.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CameraViewModel : ViewModel() {

    @Inject
    lateinit var appDatabase: AppDatabase

    init {

        App.getAppComponent().inject(this)

    }

    fun insert(uri: String, date: String) = viewModelScope.launch {
        appDatabase.photoDao().insert(PhotoModel(uri = uri, date = date))
    }

    fun insert(photoModel: MutableList<PhotoModel>) = viewModelScope.launch(Dispatchers.IO) {
        appDatabase.photoDao().insert(photoModel)
    }

}