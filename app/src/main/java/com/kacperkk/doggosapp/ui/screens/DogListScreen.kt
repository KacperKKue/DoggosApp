package com.kacperkk.doggosapp.ui.screens

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kacperkk.doggosapp.model.Dog
import com.kacperkk.doggosapp.ui.components.DogItem
import com.kacperkk.doggosapp.ui.components.SearchBar
import android.util.Log
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kacperkk.doggosapp.ui.components.Counter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogListScreen(navController: NavController) {
    var dogs by rememberSaveable {
        mutableStateOf(
            listOf(
                Dog(id = 1, name = "Burek", breed = "Labrador"),
                Dog(id = 2, name = "Reksio", breed = "Beagle", isFavorite = true),
                Dog(id = 3, name = "Łatek", breed = "Dalmatyńczyk"),
                Dog(id = 4, name = "Fafik", breed = "Mops", isFavorite = true),
                Dog(id = 5, name = "Szarik", breed = "Owczarek Niemiecki"),
                Dog(id = 6, name = "Czarek", breed = "Golden Retriever"),
                Dog(id = 7, name = "Tofik", breed = "Border Collie", isFavorite = true),
                Dog(id = 8, name = "Dyzio", breed = "Husky"),
                Dog(id = 9, name = "Pimpek", breed = "Cocker Spaniel", isFavorite = true)
            )
        )
    }


    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    var buttonsEnabled: Boolean = false;

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Doggos", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                },
                navigationIcon = {
                    val onProfileClick: () -> Unit = { navController.navigate("settings") }

                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    val onProfileClick: () -> Unit = { navController.navigate("profile") }

                    IconButton(onClick = onProfileClick) {
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
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { query ->
                    searchQuery = query
                    buttonsEnabled = query.isNotEmpty()
                },
                placeholderText = "Poszukaj lub dodaj pieska 🐕",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onAddClick = {
                    if (searchQuery.isNotEmpty() && !dogs.any { it.name.equals(searchQuery, ignoreCase = true) }) {
                        val newDog = Dog(id = dogs.size + 1, name = searchQuery, breed = "Jack Russel")
                        dogs = dogs + newDog
                        searchQuery = "" // Czyszczenie pola
                        isError = false
                        buttonsEnabled = false
                        Log.d("Add", "Dog added: $newDog")
                    } else {
                        isError = true
                        Log.d("Add", "Dog already exists or name is empty")
                    }
                },
                buttonsEnabled = buttonsEnabled,
                isError = isError
            )

            Counter(
                dogs = dogs
            )

            LazyColumn {
                // Filter dogs based on applied search query
                val filteredDogs = if (searchQuery.isNotEmpty()) {
                    dogs.filter { dog ->
                        dog.name.contains(searchQuery, ignoreCase = true) ||
                                dog.breed.contains(searchQuery, ignoreCase = true)
                    }
                } else {
                    dogs
                }

                // Sort with favorites first
                val sortedDogs = filteredDogs.sortedByDescending { it.isFavorite }

                items(sortedDogs.size) { index ->
                    DogItem(
                        dog = sortedDogs[index],
                        onDogClick = {
                            navController.navigate("dog_detail/${sortedDogs[index].id}")
                        },
                        onFavoriteClick = { clickedDog ->
                            dogs = dogs.map {
                                if (it.id == clickedDog.id) it.copy(isFavorite = !it.isFavorite) else it
                            }
                        },
                        onDeleteClick = { clickedDog ->
                            dogs = dogs.filter { it.id != clickedDog.id }
                        },
                        navController = navController
                    )
                }
            }
        }
    }
}