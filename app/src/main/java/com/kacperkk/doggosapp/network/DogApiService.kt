package com.kacperkk.doggosapp.network

import com.kacperkk.doggosapp.model.DogImage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogImage
}
