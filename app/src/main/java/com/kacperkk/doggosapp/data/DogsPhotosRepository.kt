package com.kacperkk.doggosapp.data

import com.kacperkk.doggosapp.data.network.DogsService
import com.kacperkk.doggosapp.model.DogImage

interface DogsPhotosRepository {
    suspend fun getRandomDogImage(): DogImage
}

class NetworkDogsPhotosRepository(
    private val dogsService: DogsService,
) : DogsPhotosRepository {

    override suspend fun getRandomDogImage(): DogImage = dogsService.getRandomDogImage()
}