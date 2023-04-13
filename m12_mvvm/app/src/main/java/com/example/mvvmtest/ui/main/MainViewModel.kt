package com.example.mvvmtest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val state = MutableStateFlow<State>(State.Success)
    val stateF = state.asStateFlow()

    private val text = MutableStateFlow("")
    val textF = text.asStateFlow()

    fun onSearchClick(txt: String) {
        viewModelScope.launch {
            state.value = State.Loading
            delay(3_000)
            if (txt != "") {
                text.value = txt
            }
            state.value = State.Success
        }
    }


}