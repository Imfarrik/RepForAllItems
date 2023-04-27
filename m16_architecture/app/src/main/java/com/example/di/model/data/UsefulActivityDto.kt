package com.example.di.model.data

import com.example.di.model.entity.UsefulActivity
import com.google.gson.annotations.JsonAdapter

data class UsefulActivityDto(
    @JsonAdapter(FreeValDeserializer::class)
    override val accessibility: String,
    override val activity: String,
    override val key: String,
    override val link: String,
    @JsonAdapter(FreeValDeserializer::class)
    override val participants: String,
    @JsonAdapter(FreeValDeserializer::class)
    override val price: String,
    override val type: String
) : UsefulActivity