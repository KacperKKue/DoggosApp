package com.kacperkk.doggosapp.data

import android.util.Log
import com.kacperkk.doggosapp.data.local.database.DogEntity
import com.kacperkk.doggosapp.data.local.database.DogEntityDao
import com.kacperkk.doggosapp.data.network.DogsService
import com.kacperkk.doggosapp.model.Dog
import com.kacperkk.doggosapp.model.DogImage
import com.kacperkk.doggosapp.ui.screens.adddog.AddDogViewModel.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DogsPhotosRepository {
    val dogs: Flow<List<Dog>>

    suspend fun getRandomDogImage(): DogImage
    suspend fun add(dog: Dog)
    suspend fun remove(id: Int)
    suspend fun triggerFav(id: Int)
}

class NetworkDogsPhotosRepository(
    private val dogsService: DogsService,
    private val dogDao: DogEntityDao
) : DogsPhotosRepository {

    override val dogs: Flow<List<Dog>> = dogDao.getSortedDogs().map { items ->
        items.map {
            Log.d("DogsPhotosRepository", "Dog: $it")
            Dog(
                id = it.id,
                name = it.name,
                breed = it.breed,
                imageUrl = it.imageUrl,
                isFavorite = it.isFavorite
            )
        }
    }

    override suspend fun getRandomDogImage(): DogImage = dogsService.getRandomDogImage()

    override suspend fun add(dog: Dog) {
        dogDao.insertDog(DogEntity(name = dog.name, breed = dog.breed, imageUrl = dog.imageUrl, isFavorite = false))
    }

    override suspend fun remove(id: Int) {
        dogDao.removeDog(id)
    }

    override suspend fun triggerFav(id: Int) {
        dogDao.triggerFavDog(id)
    }
}