package com.example.restapi.ui.main.model.getUserApi

import com.example.restapi.ui.main.model.adapter.FreeValDeserializer
import com.google.gson.annotations.JsonAdapter

data class Id(
    @JsonAdapter(FreeValDeserializer::class)
    val name: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val value: String?
)