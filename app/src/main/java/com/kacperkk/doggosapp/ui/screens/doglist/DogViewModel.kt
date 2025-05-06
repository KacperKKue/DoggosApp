package com.kacperkk.doggosapp.ui.screens.doglist

import android.util.Log
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

    fun getCount(): Int = when (val state = uiState.value) {
        is UiState.Success -> state.data.size
        else -> 0
    }

    fun addDog(dog: Dog) {
        viewModelScope.launch {
            dogsPhotosRepository.add(dog)
        }
    }

    fun deleteDog(dog: Dog) {
        viewModelScope.launch {
            dogsPhotosRepository.remove(dog.id)
        }
    }

    fun toggleFavorite(dog: Dog) {
        viewModelScope.launch {
            dogsPhotosRepository.triggerFav(dog.id)
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