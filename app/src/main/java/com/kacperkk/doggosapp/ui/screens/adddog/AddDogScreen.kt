package com.kacperkk.doggosapp.ui.screens.adddog

import android.R.attr.name
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kacperkk.doggosapp.DoggosApp
import com.kacperkk.doggosapp.data.AppContainer
import com.kacperkk.doggosapp.data.DogsPhotosRepository
import com.kacperkk.doggosapp.model.Dog
import com.kacperkk.doggosapp.model.DogDetailScreen
import com.kacperkk.doggosapp.ui.screens.doglist.DogsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDogScreen(
    navController: NavController,
    uiState: AddDogViewModel.UiState
) {

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
                ContentScreen(
                    uiState = uiState,
                    retryAction = { }
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {  },
                label = { Text("Imię psa") },
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Start
                )
            )

            OutlinedTextField(
                value = "",
                onValueChange = {  },
                label = { Text("Rasa") },
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Start
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Button(
                onClick = {

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
                enabled = true,
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text("Dodaj psa", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun ContentScreen(uiState: AddDogViewModel.UiState,
                  retryAction: () -> Unit) {

    when(uiState) {
        is AddDogViewModel.UiState.Loading -> LoadingScreen()
        is AddDogViewModel.UiState.Error -> ErrorScreen(
            retryAction = retryAction
        )
        is AddDogViewModel.UiState.Success -> ImageScreen(
            imageUrl = uiState.dogImage.message
        )
    }
}


@Composable
fun LoadingScreen() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = Color.DarkGray,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}


@Composable
fun ErrorScreen(retryAction: () -> Unit,
                modifier: Modifier = Modifier) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Default.Warning, contentDescription = ""
        )
        Text(text = "Failed", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Image(imageVector = Icons.Default.Refresh, contentDescription = null)
        }
    }
}

@Composable
fun ImageScreen(imageUrl: String){
    AsyncImage(
        model = imageUrl,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .fillMaxSize()
    )
}