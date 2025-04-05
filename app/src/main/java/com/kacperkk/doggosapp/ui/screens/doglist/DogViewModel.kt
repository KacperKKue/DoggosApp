package com.kacperkk.doggosapp.ui.screens.doglist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kacperkk.doggosapp.model.Dog

class DogsViewModel : ViewModel() {
    var dogs by mutableStateOf(
        listOf(
            Dog(id = 0, name = "Burek", breed = "Labrador"),
            Dog(id = 1, name = "Reksio", breed = "Beagle", isFavorite = true),
            Dog(id = 2, name = "Łatek", breed = "Dalmatyńczyk"),
            Dog(id = 3, name = "Fafik", breed = "Mops", isFavorite = true),
            Dog(id = 4, name = "Szarik", breed = "Owczarek Niemiecki"),
            Dog(id = 5, name = "Czarek", breed = "Golden Retriever"),
            Dog(id = 6, name = "Tofik", breed = "Border Collie", isFavorite = true),
            Dog(id = 7, name = "Dyzio", breed = "Husky"),
            Dog(id = 8, name = "Pimpek", breed = "Cocker Spaniel", isFavorite = true)
        )
    )
        private set

    fun addDog(newDog: Dog) {
        dogs = dogs + newDog
    }

    fun deleteDog(dog: Dog) {
        dogs = dogs.filter { it.id != dog.id }
    }

    fun toggleFavorite(dog: Dog) {
        dogs = dogs.map {
            if (it.id == dog.id) it.copy(isFavorite = !it.isFavorite) else it
        }
    }
}