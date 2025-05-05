package com.kacperkk.doggosapp.data

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kacperkk.doggosapp.data.local.database.AppDatabase
import com.kacperkk.doggosapp.data.local.database.DogEntityDao
import com.kacperkk.doggosapp.data.network.DogsService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import android.content.Context

interface AppContainer {
    val dogsPhotosRepository: DogsPhotosRepository
    val dogDao: DogEntityDao
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val dogsApiBaseUrl = "https://dog.ceo/api/breeds/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(dogsApiBaseUrl)
        .build()

    private val dogsService: DogsService by lazy {
        retrofit.create(DogsService::class.java)
    }

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    override val dogDao: DogEntityDao by lazy {
        database.dogDao()
    }

    override val dogsPhotosRepository: DogsPhotosRepository by lazy {
        NetworkDogsPhotosRepository(dogsService, dogDao)
    }
}