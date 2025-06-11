package com.frommetoyou.data.data_source

import com.frommetoyou.data.model.CityResponse
import retrofit2.Response
import retrofit2.http.GET

interface MapApi {

    @GET("cities.json/")
    suspend fun getCities() : Response<CityResponse>

}