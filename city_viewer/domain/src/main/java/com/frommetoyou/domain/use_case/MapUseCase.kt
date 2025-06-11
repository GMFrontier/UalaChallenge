package com.frommetoyou.domain.use_case

import com.frommetoyou.domain.repository.MapRepository
import javax.inject.Inject

class MapUseCase @Inject constructor(
    private val repository: MapRepository,
) {
    fun getCountries() {
            repository.getCities()
    }
}