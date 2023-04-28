package com.example.nasa.di

import com.example.nasa.ui.MainViewModel
import com.example.nasa.ui.PagingViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainViewModel: MainViewModel)
    fun inject(pagingViewModel: PagingViewModel)

}