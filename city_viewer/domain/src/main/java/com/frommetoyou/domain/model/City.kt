package com.frommetoyou.domain.model

data class City(
    val name: String,
    val country: String,
    val id: Long,
    val coordinates: Coordinates,
    val isFavorite: Boolean
)

data class Coordinates(
    val lon: Double,
    val lat: Double
)
