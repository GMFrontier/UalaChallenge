package com.frommetoyou.domain.repository

import com.frommetoyou.domain.model.City
import kotlinx.coroutines.flow.Flow
import com.frommetoyou.core.util.Result

interface MapRepository {
    fun getCities(): Flow<Result<City>>
}