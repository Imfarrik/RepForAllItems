package com.example.nasa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.nasa.App
import com.example.nasa.network.ServiceApi
import com.example.nasa.ui.adapters.PagingTaskSource
import javax.inject.Inject

class PagingViewModel : ViewModel() {

    @Inject
    lateinit var serviceApi: ServiceApi

    init {
        App.getAppComponent().inject(this)
    }

    val data = Pager(PagingConfig(pageSize = 1)) {
        PagingTaskSource(serviceApi)
    }.flow.cachedIn(viewModelScope)

}