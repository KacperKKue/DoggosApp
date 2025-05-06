package com.kacperkk.doggosapp.ui.screens.doglist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kacperkk.doggosapp.DoggosApp
import com.kacperkk.doggosapp.data.DogsPhotosRepository
import com.kacperkk.doggosapp.model.Dog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DogsViewModel(
    private val dogsPhotosRepository: DogsPhotosRepository
) : ViewModel() {
    var dogs by mutableStateOf(
        listOf(
            Dog(id = 0, name = "Burek", breed = "Labrador", imageUrl = "https://images.dog.ceo/breeds/boxer/n02108089_2791.jpg"),
            Dog(id = 1, name = "Reksio", breed = "Beagle", isFavorite = true, imageUrl = "https://images.dog.ceo/breeds/terrier-australian/n02096294_1449.jpg"),
            Dog(id = 2, name = "Łatek", breed = "Dalmatyńczyk", imageUrl = "https://images.dog.ceo/breeds/hound-english/n02089973_2300.jpg"),
            Dog(id = 3, name = "Fafik", breed = "Mops", isFavorite = true, imageUrl = "https://images.dog.ceo/breeds/poodle-medium/WhatsApp_Image_2022-08-06_at_4.48.38_PM.jpg"),
        )
    )
        private set

    sealed interface UiState {
        object Loading: UiState
        data class Error(val throwable: Throwable): UiState
        data class Success(val data: List<Dog>): UiState
    }

    val uiState: StateFlow<UiState> = dogsPhotosRepository
        .dogs
        .map<List<Dog>, UiState> { UiState.Success(data = it) }
        .catch { emit(UiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DoggosApp)
                val dogsPhotosRepository = application.container.dogsPhotosRepository
                DogsViewModel(dogsPhotosRepository)
            }
        }
    }
}