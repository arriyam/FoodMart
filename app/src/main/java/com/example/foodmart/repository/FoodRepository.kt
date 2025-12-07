package com.example.foodmart.repository

import arrow.core.Either
import com.example.foodmart.model.FoodCategories
import com.example.foodmart.model.FoodItem

interface FoodRepository {
    suspend fun getFoodItems(): Either<Throwable, List<FoodItem>>
    suspend fun getFoodCategories(): Either<Throwable, List<FoodCategories>>
}