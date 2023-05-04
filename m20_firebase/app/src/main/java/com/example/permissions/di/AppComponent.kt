package com.example.permissions.di

import com.example.permissions.ui.fragment.camera.CameraViewModel
import com.example.permissions.ui.fragment.gallery.GalleryViewModel
import com.example.permissions.ui.fragment.map.MapViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(cameraViewModel: CameraViewModel)
    fun inject(galleryViewModel: GalleryViewModel)
    fun inject(mapViewModel: MapViewModel)

}