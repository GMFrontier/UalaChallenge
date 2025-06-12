package com.frommetoyou.domain.repository

import androidx.paging.PagingSource
import com.frommetoyou.domain.model.City

interface LocalRepository {
    fun getCities(filter: String = ""): PagingSource<Int, City>
    suspend fun saveAllCities(cities: List<City>): Unit
    suspend fun deleteCities(): Int
}