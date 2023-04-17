package com.example.restapi.ui.main.model.getUserApi

import com.example.restapi.ui.main.model.adapter.FreeValDeserializer
import com.google.gson.annotations.JsonAdapter

data class Dob(
    @JsonAdapter(FreeValDeserializer::class)
    val age: String?,
    val date: String?
)