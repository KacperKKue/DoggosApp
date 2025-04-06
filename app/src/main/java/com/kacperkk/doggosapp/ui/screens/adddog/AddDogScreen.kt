package com.kacperkk.doggosapp.ui.screens.adddog

import android.R.attr.name
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kacperkk.doggosapp.model.Dog
import com.kacperkk.doggosapp.network.RetrofitInstance
import com.kacperkk.doggosapp.ui.screens.doglist.DogsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDogScreen(
    navController: NavController,
    dogViewModel: DogsViewModel
) {
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }

    var imageUrl by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var imageError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        imageUrl = try {
            val url = fetchRandomDogImageUrl()
            imageError = url == "error"
            url
        } catch (e: Exception) {
            imageError = true
            "error"
        }
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dodaj Psa",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(280.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(color = Color.DarkGray)
                    }
                    !imageError && imageUrl.isNotBlank() -> {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Zdjęcie psa",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {
                        Text(
                            text = "🐶",
                            fontSize = 48.sp
                        )
                    }
                }
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Imię psa") },
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Start
                )
            )

            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = { Text("Rasa psa") },
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Start
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Button(
                onClick = {
                    if (name.isNotBlank() && breed.isNotBlank()) {
                        dogViewModel.addDog(
                            Dog(
                                id = dogViewModel.dogs.size + 1,
                                name = name,
                                breed = breed,
                                imageUrl = if (!imageError) imageUrl else null
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .width(280.dp)
                    .height(50.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFF5C258D), Color(0xFFD6A4A4))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(8.dp),
                enabled = name.isNotBlank() && breed.isNotBlank(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text("Dodaj psa", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

suspend fun fetchRandomDogImageUrl(): String {
    return try {
        val response = RetrofitInstance.api.getRandomDogImage()
        response.message // <-- link do zdjęcia
    } catch (e: Exception) {
        Log.e("AddDogScreen", "Błąd pobierania obrazka", e)
        "error"
    }
}
