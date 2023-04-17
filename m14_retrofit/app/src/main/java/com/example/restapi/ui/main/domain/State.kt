package com.example.restapi.ui.main.domain

sealed class State {
    object Loading : State()
    object Success : State()
    data class Error(val message: String) : State()
}

