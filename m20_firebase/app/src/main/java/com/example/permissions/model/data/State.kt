package com.example.permissions.model.data

import com.example.permissions.model.PhotoModel
import com.example.permissions.model.data.apiAll.ApiAll

sealed class State {
    object Loading : State()
    data class Success(
        val onSuccess: MutableList<PhotoModel>? = null,
        val onSuccessApi: ApiAll? = null
    ) : State()

    object Error : State()
}

