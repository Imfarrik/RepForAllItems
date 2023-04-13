package com.example.mvvmtest.ui.main

sealed class State {
    object Loading : State()
    object Success : State()
}
