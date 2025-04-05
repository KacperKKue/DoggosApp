package com.kacperkk.doggosapp.ui.screens.adddog

import android.R.attr.name
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun AddDogScreen(
    navController: NavController,
    dogViewModel: DogsViewModel
) {
    var name: String = ""
    var breed: String = ""

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dodaj psa",
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
                .padding(paddingValues)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(256.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(101, 85, 143), Color(238, 184, 224))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🐕", fontSize = 16.sp)
            }

            OutlinedTextField(
                value = name,
                onValueChange = { },
                placeholder = {
                    Text(
                        text = "Imie",
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .weight(0.6f)
                    .height(16.dp),
            )

            // Pole tekstowe - Rasa
            OutlinedTextField(
                value = breed,
                onValueChange = { },
                placeholder = {
                    Text(
                        text = "Rasa",
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .weight(0.6f)
                    .height(16.dp),
            )

            // Przycisk dodania
            Button(
                onClick = {
                    // Tu możesz wywołać dogViewModel.addDog(...) później
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF5C258D), Color(0xFFD6A4A4))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Text("Add", color = Color.White)
            }
        }
    }
}