package com.example.foodmart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodItem(
    @SerialName("category_uuid") val categoryUuid: String,
    @SerialName("image_url") val imageUrl: String,
    val name: String,
    val price: Double,
    val uuid: String
)