package com.example.permissions.ui.fragment.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.permissions.App
import com.example.permissions.model.PhotoModel
import com.example.permissions.model.data.State
import com.example.permissions.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryViewModel : ViewModel() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()


    init {
        App.getAppComponent().inject(this)
        getDB()
    }

    fun getDB() = viewModelScope.launch(Dispatchers.IO) {
        _state.value = State.Loading

        val data = State.Success(appDatabase.photoDao().getAll())

        delay(1000)

        if (!data.onSuccess.isNullOrEmpty()) {
            _state.value = data
        } else {
            _state.value = State.Error
        }
    }

    fun deletePhoto(photoModel: PhotoModel) = viewModelScope.launch(Dispatchers.IO) {
        appDatabase.photoDao().delete(photoModel)
    }


}