package com.kacperkk.doggosapp.data.network

import com.kacperkk.doggosapp.model.DogImage
import retrofit2.http.GET

interface DogsService {
    @GET("image/random")
    suspend fun getRandomDogImage(): DogImage
}