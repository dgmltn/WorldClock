package com.dgmltn.worldclock.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dgmltn.worldclock.domain.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun observeAllCities(): Flow<List<City>>

    @Upsert
    suspend fun addCity(city: City)

    @Query("DELETE FROM city WHERE id = :cityId")
    suspend fun deleteCity(cityId: String)
}