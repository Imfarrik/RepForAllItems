package com.example.nasa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.nasa.App
import com.example.nasa.network.ServiceApi
import com.example.nasa.network.State
import com.example.nasa.ui.adapters.PagingTaskSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel : ViewModel() {

    @Inject
    lateinit var serviceApi: ServiceApi

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()


    init {
        App.getAppComponent().inject(this)
        getData()
    }

    val data = Pager(PagingConfig(pageSize = 1)) {
        PagingTaskSource(serviceApi)
    }.flow.cachedIn(viewModelScope)

    private fun getData() {

        _state.value = State.Loading

        compositeDisposable.add(
            serviceApi.getPhotosCuriosity().subscribe({
                _state.value = State.Success(it)
            }, {
                _state.value = State.ServerError(it.message ?: "Error 5xx")
            })
        )

    }

}