package com.example.nasa.network

import com.example.nasa.model.ApiNasaCuriosity

sealed class State {
    object Loading : State()
    data class Success(val onSuccess: ApiNasaCuriosity) : State()
    data class ServerError(val message: String) : State()
}
