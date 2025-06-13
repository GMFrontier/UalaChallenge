package com.frommetoyou.domain.repository

import androidx.paging.PagingSource
import com.frommetoyou.domain.model.City

interface LocalRepository {
    fun getCities(
        filter: String = "",
        onlyFavorites: Boolean = false
    ): PagingSource<Int, City>

    suspend fun saveAllCities(cities: List<City>): Unit
    fun deleteCities(): Int
    fun saveCity(city: City): Long
    suspend fun countCities(filter: String, onlyFavorites: Boolean): Int
}