package com.frommetoyou.data.data_source

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frommetoyou.domain.model.City

@Dao
interface CityDao {
    @Query("SELECT * FROM city WHERE name LIKE :filter OR country LIKE :filter ORDER BY name ASC")
    fun getCities(filter: String): PagingSource<Int, City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCities(cities: List<City>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCity(city: City): Long

    @Query("DELETE FROM city")
    suspend fun deleteCities(): Int
}