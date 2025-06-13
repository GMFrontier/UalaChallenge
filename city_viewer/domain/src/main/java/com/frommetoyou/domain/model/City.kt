package com.frommetoyou.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "city")
data class City(
    val name: String,
    val country: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val coordinates: Coordinates,
    val isFavorite: Boolean
) {
    companion object {
        fun getDefaultCity(): City {
            return City(
                name = "Buenos Aires",
                country = "AR",
                id = 1L,
                coordinates = Coordinates(-58.377232, -34.613152),
                isFavorite = false
            )
        }
    }
}

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
