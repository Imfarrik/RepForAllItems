package com.example.restapi.ui.main.model.getUserApi

import com.example.restapi.ui.main.model.adapter.FreeValDeserializer
import com.google.gson.annotations.JsonAdapter

data class Location(
    @JsonAdapter(FreeValDeserializer::class)
    val city: String?,
    val coordinates: Coordinates?,
    @JsonAdapter(FreeValDeserializer::class)
    val country: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val postcode: String?,
    @JsonAdapter(FreeValDeserializer::class)
    val state: String?,
    val street: Street?,
    val timezone: Timezone?
)