package com.kacperkk.doggosapp.ui.screens.doglist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kacperkk.doggosapp.model.Dog

class DogsViewModel : ViewModel() {
    var dogs by mutableStateOf(
        listOf(
            Dog(id = 0, name = "Burek", breed = "Labrador", imageUrl = "https://images.dog.ceo/breeds/boxer/n02108089_2791.jpg"),
            Dog(id = 1, name = "Reksio", breed = "Beagle", isFavorite = true, imageUrl = "https://images.dog.ceo/breeds/terrier-australian/n02096294_1449.jpg"),
            Dog(id = 2, name = "Łatek", breed = "Dalmatyńczyk", imageUrl = "https://images.dog.ceo/breeds/hound-english/n02089973_2300.jpg"),
            Dog(id = 3, name = "Fafik", breed = "Mops", isFavorite = true, imageUrl = "https://images.dog.ceo/breeds/poodle-medium/WhatsApp_Image_2022-08-06_at_4.48.38_PM.jpg"),
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