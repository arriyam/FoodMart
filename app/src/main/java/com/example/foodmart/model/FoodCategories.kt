package com.example.foodmart.model

import kotlinx.serialization.Serializable

@Serializable
data class FoodCategory(
    val name: String,
    val uuid: String
)