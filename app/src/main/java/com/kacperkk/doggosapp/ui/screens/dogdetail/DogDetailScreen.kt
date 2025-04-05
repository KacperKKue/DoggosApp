package com.kacperkk.doggosapp.ui.screens.dogdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kacperkk.doggosapp.model.Dog
import com.kacperkk.doggosapp.ui.screens.doglist.DogsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(
    navController: NavController,
    dogViewModel: DogsViewModel,
    dogId: String
) {
    // Convert dogId to Int and find the dog in the ViewModel's list
    val dogIdInt = dogId.toIntOrNull() ?: -1
    val dog = dogViewModel.dogs.firstOrNull { it.id == dogIdInt }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detale", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Delete button with ViewModel interaction
                    IconButton(onClick = {
                        dog?.let {
                            dogViewModel.deleteDog(it)
                            navController.popBackStack() // Navigate back after deletion
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (dog == null) {
            // Handle invalid dogId case
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Dog not found!")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Display dog details
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(101, 85, 143),
                                    Color(238, 184, 224)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset.Infinite
                            )
                        )
                ) {
                    Text(
                        text = "🐕",
                        fontSize = 64.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dog.name, // Use actual dog name
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = dog.breed, // Use actual dog breed
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}