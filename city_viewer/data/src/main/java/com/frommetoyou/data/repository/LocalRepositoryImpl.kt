package com.frommetoyou.data.repository

import androidx.paging.PagingSource
import androidx.room.Transaction
import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import com.frommetoyou.data.data_source.CityDao
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dao: CityDao,
    private val dispatchers: CoroutinesDispatcherProvider
) : LocalRepository {
    override fun getCities(
        filter: String,
        onlyFavorites: Boolean
    ): PagingSource<Int, City> {
        return dao.getCities(filter, onlyFavorites)
    }

    @Transaction
    override suspend fun saveAllCities(cities: List<City>) {
        dao.saveAllCities(cities)
    }

    override suspend fun deleteCities(): Int {
        return dao.deleteCities()
    }

    override fun saveCity(city: City): Long {
        return dao.saveCity(city)
    }

    override suspend fun countCities(filter: String, onlyFavorites: Boolean): Int {
        return dao.countCities(filter, onlyFavorites)
    }


}