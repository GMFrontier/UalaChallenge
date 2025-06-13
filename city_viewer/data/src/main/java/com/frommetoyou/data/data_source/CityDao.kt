package com.frommetoyou.data.data_source

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frommetoyou.domain.model.City

@Dao
interface CityDao {
    @Query("""
         SELECT * FROM city 
        WHERE name LIKE :filter || '%' COLLATE NOCASE
        AND (:onlyFavorites == 0 OR isFavorite = 1)
        ORDER BY name ASC
    """)
    fun getCities(filter: String, onlyFavorites: Boolean): PagingSource<Int, City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCities(cities: List<City>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCity(city: City): Long

    @Query("DELETE FROM city")
    suspend fun deleteCities(): Int

    @Query("""
        SELECT COUNT(*) FROM city
        WHERE (:query IS NULL OR name LIKE '%' || :query || '%' OR country LIKE '%' || :query || '%')
        AND (:onlyFavorites = 0 OR isFavorite = 1)
    """)
    suspend fun countCities(query: String?, onlyFavorites: Boolean): Int
}