package com.frommetoyou.data.model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("_id")
    val id: Long,
    @SerializedName("coord")
    val coordinates: CoordResponse
)

data class CoordResponse(
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double
)
