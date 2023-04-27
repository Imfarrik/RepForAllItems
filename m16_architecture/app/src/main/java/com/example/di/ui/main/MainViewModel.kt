package com.example.di.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.domain.GetUsefulActivityUseCase
import com.example.di.model.entity.UsefulActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val getUsefulActivityUseCase: GetUsefulActivityUseCase) :
    ViewModel() {

    private val _usefulActivity = MutableStateFlow<UsefulActivity?>(null)
    val usefulActivity: StateFlow<UsefulActivity?> = _usefulActivity

    fun reloadUsefulActivity() {
        viewModelScope.launch {
            _usefulActivity.value = getUsefulActivityUseCase.execute()
        }
    }


}