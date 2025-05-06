package com.kacperkk.doggosapp.ui.screens.doglist

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.kacperkk.doggosapp.model.Dog
import com.kacperkk.doggosapp.ui.components.DogItem
import com.kacperkk.doggosapp.ui.components.SearchBar
import com.kacperkk.doggosapp.ui.components.Counter
import com.kacperkk.doggosapp.model.AddDogScreen
import com.kacperkk.doggosapp.model.DogDetailScreen
import com.kacperkk.doggosapp.model.ProfileScreen
import com.kacperkk.doggosapp.model.SettingsScreen
import com.kacperkk.doggosapp.ui.screens.doglist.DogsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogListScreen(
    navController: NavController,
    dogViewModel: DogsViewModel,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val uiState by dogViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Doggos",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(SettingsScreen) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(ProfileScreen) }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                placeholderText = "Poszukaj lub dodaj pieska 🐕",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onAddClick = {
                    navController.navigate(AddDogScreen)
                }
            )

            when (uiState) {
                is DogsViewModel.UiState.Loading -> {
                    Text("Loading...", modifier = Modifier.padding(16.dp))
                }
                is DogsViewModel.UiState.Error -> {
                    val error = (uiState as DogsViewModel.UiState.Error).throwable
                    Text(
                        text = "Error: ${error.localizedMessage}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is DogsViewModel.UiState.Success -> {
                    val dogs = (uiState as DogsViewModel.UiState.Success).data

                    Counter(dogs = dogs)

                    val filteredDogs = if (searchQuery.isNotEmpty()) {
                        dogs.filter {
                            it.name.contains(searchQuery, ignoreCase = true) ||
                                    it.breed.contains(searchQuery, ignoreCase = true)
                        }
                    } else dogs

                    val sortedDogs = filteredDogs.sortedByDescending { it.isFavorite }

                    LazyColumn {
                        items(sortedDogs.size) { index ->
                            val dog = sortedDogs[index]
                            Log.d("DogListScreen", "Dog: $dog")
                            DogItem(
                                dog = dog,
                                onDogClick = { navController.navigate(DogDetailScreen(dogId = dog.id)) },
                                onFavoriteClick = { dogViewModel.toggleFavorite(dog) },
                                onDeleteClick = { dogViewModel.deleteDog(dog) },
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
