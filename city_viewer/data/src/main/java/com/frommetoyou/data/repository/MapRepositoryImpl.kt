package com.frommetoyou.data.repository

import com.frommetoyou.core.extensions.getResponseError
import com.frommetoyou.core.extensions.parseResponse
import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import com.frommetoyou.core.util.Result
import com.frommetoyou.data.data_source.MapApi
import com.frommetoyou.data.mapper.mapSuccess
import com.frommetoyou.data.mapper.toCity
import com.frommetoyou.data.model.CityResponse
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val api: MapApi,
    private val dispatchers: CoroutinesDispatcherProvider
) : MapRepository {
    override fun getCities(): Flow<Result<City>> = flow {
        emit(api.getCities())
    }
        .catch { emit(it.getResponseError()) }
        .map { it.parseResponse() }
        .map { result -> result.mapSuccess { it.toCity() } }
        .flowOn(dispatchers.io)

}