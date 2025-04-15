package com.kacperkk.doggosapp.ui.screens.adddog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kacperkk.doggosapp.data.DogsPhotosRepository
import com.kacperkk.doggosapp.DoggosApp
import kotlinx.coroutines.launch

class AddDogViewModel(
    private val dogsPhotosRepository: DogsPhotosRepository
) : ViewModel() {

    sealed interface UiState {
        data class Success(
            val name: String = "",
            val breed: String = "",
            val imageUrl: String
        ) : UiState

        object Error : UiState
        object Loading : UiState
    }

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        getRandomDogImage()
    }

    fun getRandomDogImage() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val currentState = uiState as? UiState.Success
                val imageUrl = dogsPhotosRepository.getRandomDogImage().message
                UiState.Success(
                    name = currentState?.name ?: "",
                    breed = currentState?.breed ?: "",
                    imageUrl = imageUrl
                )
            } catch (e: Exception) {
                UiState.Error
            }
        }
    }

    fun updateName(name: String) {
        if (uiState is UiState.Success) {
            uiState = (uiState as UiState.Success).copy(name = name)
        }
    }

    fun updateBreed(breed: String) {
        if (uiState is UiState.Success) {
            uiState = (uiState as UiState.Success).copy(breed = breed)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DoggosApp)
                val dogsPhotosRepository = application.container.dogsPhotosRepository
                AddDogViewModel(dogsPhotosRepository)
            }
        }
    }
}