package com.frommetoyou.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.frommetoyou.core.extensions.withLoadingState
import com.frommetoyou.core.util.Result
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.model.CityFilter
import com.frommetoyou.domain.repository.LocalRepository
import com.frommetoyou.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapUseCase @Inject constructor(
    private val repository: MapRepository,
    private val localRepository: LocalRepository
) {

    fun ensureCitiesExistIfNeeded(filter: CityFilter = CityFilter()): Flow<Result<Any>> =
        flow {
            val citiesCount =
                localRepository.countCities(filter.query, filter.onlyFavorites)
            if (citiesCount == 0) {
                repository.getCities().withLoadingState()
                    .collect { remoteResult ->
                        if (remoteResult is Result.Success) {
                            val cities = remoteResult.data
                            val chunkSize = 100
                            emit(Result.Success(true))
                            cities.chunked(chunkSize).forEach { chunk ->
                                localRepository.saveAllCities(chunk)
                            }
                        }
                    }
            }
        }

    fun getCitiesPaged(filter: CityFilter): Flow<PagingData<City>> = flow {
        emitAll(
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    localRepository.getCities(
                        filter.query,
                        filter.onlyFavorites
                    )
                }
            ).flow
        )
    }

    suspend fun deleteCities(): Int {
        return localRepository.deleteCities()
    }

    fun saveCity(city: City) {
        localRepository.saveCity(city)
    }
}
