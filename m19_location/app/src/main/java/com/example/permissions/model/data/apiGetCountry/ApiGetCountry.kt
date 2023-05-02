package com.example.permissions.model.data.apiGetCountry

data class ApiGetCountry(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val population: Int,
    val status: String,
    val timezone: String
)