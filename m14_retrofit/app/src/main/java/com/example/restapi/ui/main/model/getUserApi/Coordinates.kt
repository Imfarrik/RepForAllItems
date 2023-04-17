package com.example.restapi.ui.main.model.getUserApi

import com.example.restapi.ui.main.model.adapter.FreeValDeserializer
import com.google.gson.annotations.JsonAdapter

data class Coordinates(
    @JsonAdapter(FreeValDeserializer::class)
    val latitude: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val longitude: String?
)