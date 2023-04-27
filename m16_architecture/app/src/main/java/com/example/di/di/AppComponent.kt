package com.example.di.di

import com.example.di.ui.main.MainFragment
import com.example.di.ui.main.MainViewModel
import com.example.di.ui.main.MainViewModelFactory
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun mainViewModelFactory(): MainViewModelFactory

}