package com.example.foodmart.model

import kotlinx.serialization.Serializable

@Serializable
data class FoodCategories(
    val name: String,
    val uuid: String
)