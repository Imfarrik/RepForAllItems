package com.example.permissions.ui.fragment.map

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.permissions.App
import com.example.permissions.model.data.State
import com.example.permissions.model.network.ServiceApi
import com.yandex.runtime.Runtime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MapViewModel : ViewModel() {

    @Inject
    lateinit var serviceApi: ServiceApi

    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    init {
        App.getAppComponent().inject(this)
    }

    @SuppressLint("CheckResult")
    fun getAll(lat: Double, lon: Double) {
        _state.value = State.Loading
        serviceApi.getAll(lon = lon, lat = lat).subscribe({ all ->
            _state.value = State.Success(onSuccessApi = all)
        }, {})

    }


}

