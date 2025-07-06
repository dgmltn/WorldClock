package com.dgmltn.worldclock.di

import com.dgmltn.worldclock.data.CityRepository
import com.dgmltn.worldclock.data.CityRepositoryImplRoom
import com.dgmltn.worldclock.data.GeocoderRepository
import com.dgmltn.worldclock.data.GeocoderRepositoryImplOpenMeteo
import com.dgmltn.worldclock.data.WorldClockDatabase
import com.dgmltn.worldclock.ui.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Database
    single { WorldClockDatabase.getInstance(androidContext()) }

    // DAOs
    single { get<WorldClockDatabase>().cityDao() }

    // Repositories
    single<GeocoderRepository> { GeocoderRepositoryImplOpenMeteo(get()) }
    single<CityRepository> { CityRepositoryImplRoom(get()) }

    // Utility
    single<HttpClient> {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    // ViewModels
    viewModelOf(::MainViewModel)
}
