package com.frommetoyou.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable

@Entity(tableName = "city")
@Serializable
data class City(
    val name: String,
    val country: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val coordinates: Coordinates,
    val isFavorite: Boolean
)

@Serializable
data class Coordinates(
    val lon: Double,
    val lat: Double
)

data class CityFilter(
    val query: String = "",
    val onlyFavorites: Boolean = false
)

class CoordinatesConverter {

    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates): String {
        return "${coordinates.lat},${coordinates.lon}"
    }

    @TypeConverter
    fun toCoordinates(value: String): Coordinates {
        val parts = value.split(",")
        return Coordinates(parts[1].toDouble(), parts[0].toDouble())
    }
}
