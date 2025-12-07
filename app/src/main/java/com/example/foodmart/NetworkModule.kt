package com.example.foodmart

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val jsonFormat = Json { ignoreUnknownKeys = true }

val client = HttpClient {
    install(Logging){
        level = LogLevel.ALL
    }
    install(ContentNegotiation){
        json(
            json = jsonFormat
        )
    }
}