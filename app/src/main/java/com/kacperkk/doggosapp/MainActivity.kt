package com.kacperkk.doggosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kacperkk.doggosapp.model.AddDogScreen
import com.kacperkk.doggosapp.model.DogDetailScreen
import com.kacperkk.doggosapp.model.DogListScreen
import com.kacperkk.doggosapp.model.ProfileScreen
import com.kacperkk.doggosapp.model.SettingsScreen
import com.kacperkk.doggosapp.ui.screens.adddog.AddDogScreen
import com.kacperkk.doggosapp.ui.screens.adddog.AddDogViewModel
import com.kacperkk.doggosapp.ui.screens.dogdetail.DogDetailScreen
import com.kacperkk.doggosapp.ui.screens.doglist.DogListScreen
import com.kacperkk.doggosapp.ui.screens.doglist.DogsViewModel
import com.kacperkk.doggosapp.ui.screens.profile.ProfileScreen
import com.kacperkk.doggosapp.ui.screens.settings.SettingsScreen
import com.kacperkk.doggosapp.ui.theme.DoggosAppTheme

class MainActivity : ComponentActivity() {
    //private val dogViewModel: DogsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoggosAppTheme {
                DoggosApplication()
            }
        }
    }
}

@Composable
fun DoggosApplication() {

    val navController = rememberNavController()

    val dogViewModel: DogsViewModel =
        viewModel(factory = DogsViewModel.Factory)

    val addDogViewModel: AddDogViewModel =
        viewModel(factory = AddDogViewModel.Factory)


    NavHost(navController = navController, startDestination = DogListScreen) {
        composable<DogListScreen> {
            DogListScreen(
                navController = navController,
                dogViewModel = dogViewModel
            )
        }

        composable<ProfileScreen> {
            ProfileScreen(navController)
        }

        composable<SettingsScreen> {
            SettingsScreen(navController)
        }

        composable<AddDogScreen> {
            AddDogScreen(
                navController,
                dogViewModel,
                addDogViewModel
            )
        }

        composable<DogDetailScreen> {
            val args = it.toRoute<DogDetailScreen>()
            DogDetailScreen(
                navController = navController,
                dogViewModel = dogViewModel,
                dogId = args.dogId
            )
        }
    }
}
