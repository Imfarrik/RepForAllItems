package com.example.restapi.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapi.ui.main.domain.State
import com.example.restapi.ui.main.model.getUserApi.GetUserApi
import com.example.restapi.ui.main.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()

    private val _getUser = MutableStateFlow<GetUserApi?>(null)
    val getUser = _getUser.asStateFlow()


    init {

        getUser()

    }

    fun getUser() {

        viewModelScope.launch {
            _state.value = State.Loading

            val response = RetrofitInstance.usersApi.getUser()

            if (response.isSuccessful) {
                _state.value = State.Success
                _getUser.value = response.body()
            } else {
                _state.value = State.Error(response.code().toString())
            }

        }

    }

}