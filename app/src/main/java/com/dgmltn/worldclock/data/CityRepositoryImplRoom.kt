package com.dgmltn.worldclock.data

import com.dgmltn.worldclock.domain.City
import kotlinx.coroutines.flow.Flow

class CityRepositoryImplRoom(private val dao: CityDao): CityRepository {
    override fun listAll(): Flow<List<City>> =
        dao.observeAllCities()

    override suspend fun addCity(city: City) {
        dao.addCity(city)
    }

    override suspend fun removeCity(cityId: String) {
        dao.deleteCity(cityId)
    }
}