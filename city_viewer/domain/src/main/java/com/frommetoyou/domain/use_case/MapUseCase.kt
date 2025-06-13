package com.frommetoyou.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.frommetoyou.core.util.Result
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.model.CityFilter
import com.frommetoyou.domain.repository.LocalRepository
import com.frommetoyou.domain.repository.MapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapUseCase @Inject constructor(
    private val repository: MapRepository,
    private val localRepository: LocalRepository
) {

    fun getCities(): Flow<Result<List<City>>> = flow {
        val localCountries = localRepository.getCities("").load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = Int.MAX_VALUE,
                placeholdersEnabled = false
            )
        ).let { result ->
            when (result) {
                is PagingSource.LoadResult.Page -> result.data
                else -> emptyList()
            }
        }

        if (localCountries.isNotEmpty()) {
            emit(Result.Success(localCountries))
        } else {
            repository.getCities().collect { remoteResult ->
                if (remoteResult is Result.Success) {
                    val cities = remoteResult.data
                    val chunkSize = 100
                    emit(remoteResult)

                    cities.chunked(chunkSize).forEach { chunk ->
                        withContext(Dispatchers.IO) {
                            launch {
                                localRepository.saveAllCities(chunk)
                            }
                        }
                    }
                }
            }
        }
    }

    fun getCitiesPaged(filter: CityFilter): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { localRepository.getCities(filter.query, filter.onlyFavorites) }
        ).flow
    }

    suspend fun deleteCities(): Int {
        return localRepository.deleteCities()
    }

    fun saveCity(city: City) {
        localRepository.saveCity(city)
    }
}
