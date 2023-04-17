package com.example.restapi.ui.main.model.getUserApi

import com.example.restapi.ui.main.model.adapter.FreeValDeserializer
import com.google.gson.annotations.JsonAdapter

data class Info(
    @JsonAdapter(FreeValDeserializer::class)
    val page: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val results: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val seed: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val version: String?
)