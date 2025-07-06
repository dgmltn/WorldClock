package com.dgmltn.worldclock.data

import com.dgmltn.worldclock.domain.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun listAll(): Flow<List<City>>

    suspend fun addCity(city: City)

    suspend fun removeCity(cityId: String)
}