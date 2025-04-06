package com.kacperkk.doggosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kacperkk.doggosapp.ui.screens.adddog.AddDogScreen
import com.kacperkk.doggosapp.ui.screens.dogdetail.DogDetailScreen
import com.kacperkk.doggosapp.ui.screens.doglist.DogListScreen
import com.kacperkk.doggosapp.ui.screens.doglist.DogsViewModel
import com.kacperkk.doggosapp.ui.screens.profile.ProfileScreen
import com.kacperkk.doggosapp.ui.screens.settings.SettingsScreen
import com.kacperkk.doggosapp.ui.theme.DoggosAppTheme

class MainActivity : ComponentActivity() {
    private val dogViewModel: DogsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoggosAppTheme {
                DoggosApplication(dogViewModel = dogViewModel)
            }
        }
    }
}

@Composable
fun DoggosApplication(dogViewModel: DogsViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dog_list") {
        composable("dog_list") {
            DogListScreen(
                navController = navController,
                dogViewModel = dogViewModel,
                onDeleteDog = { dog -> dogViewModel.deleteDog(dog) },
                onToggleFavorite = { dog -> dogViewModel.toggleFavorite(dog) }
            )
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
        composable("new_dog") {
            AddDogScreen(navController, dogViewModel)
        }
        composable("dog_detail/{dogId}") { backStackEntry ->
            val dogId = backStackEntry.arguments?.getString("dogId") ?: "Unknown"
            DogDetailScreen(
                navController = navController,
                dogViewModel = dogViewModel,
                dogId = dogId
            )
        }
    }
}