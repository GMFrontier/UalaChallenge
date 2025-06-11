package com.frommetoyou.data.mapper

import com.frommetoyou.data.model.CityResponse
import com.frommetoyou.data.model.CoordResponse
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.model.Coordinates
import com.frommetoyou.core.util.Result

fun CityResponse.toCity(): City {
    return City(
        name = name,
        country = country,
        id = id,
        coordinates = coordinates.toCoordinates(),
        isFavorite = false
    )
}
fun CoordResponse.toCoordinates(): Coordinates {
    return Coordinates(
        lon = lon,
        lat = lat
    )
}

inline fun <T, R> Result<T>.mapSuccess(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Failure -> this
    is Result.Loading -> this
    is Result.NotLoading -> this
}