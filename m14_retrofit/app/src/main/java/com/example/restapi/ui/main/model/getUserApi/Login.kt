package com.example.restapi.ui.main.model.getUserApi

import com.example.restapi.ui.main.model.adapter.FreeValDeserializer
import com.google.gson.annotations.JsonAdapter

data class Login(
    @JsonAdapter(FreeValDeserializer::class)
    val md5: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val password: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val salt: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val sha1: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val sha256: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val username: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val uuid: String?
)