package com.example.foodmart.repository

import arrow.core.Either
import arrow.core.right
import com.example.foodmart.client
import com.example.foodmart.jsonFormat
import com.example.foodmart.model.FoodCategories
import com.example.foodmart.model.FoodItem
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.serialization.decodeFromString

class FoodRepositoryImpl : FoodRepository {
    override suspend fun getFoodItems(): Either<Throwable, List<FoodItem>> = Either.catch {
        val data = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = API_HOST
                path(FOOD_ITEMS_PATH)
            }
        }.bodyAsText()
        jsonFormat.decodeFromString(data)
    }

    override suspend fun getFoodCategories(): Either<Throwable, List<FoodCategories>> = Either.catch {
        val data = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = API_HOST
                path(FOOD_CATEGORIES_PATH)
            }
        }.bodyAsText()
        jsonFormat.decodeFromString(data)
    }

    companion object {
        private const val API_HOST = "7shifts.github.io/mobile-takehome/api"
        private const val FOOD_ITEMS_PATH = "food_items.json"
        private const val FOOD_CATEGORIES_PATH = "food_item_categories.json"
    }
}