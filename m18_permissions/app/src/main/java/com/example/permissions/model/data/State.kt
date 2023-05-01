package com.example.permissions.model.data

import com.example.permissions.model.PhotoModel

sealed class State {
    object Loading : State()
    data class Success(val onSuccess: MutableList<PhotoModel>?) : State()
    object Error : State()
}

