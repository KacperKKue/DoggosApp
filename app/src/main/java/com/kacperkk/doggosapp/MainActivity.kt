package com.kacperkk.doggosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kacperkk.doggosapp.ui.screens.DogDetailScreen
import com.kacperkk.doggosapp.ui.screens.DogListScreen
import com.kacperkk.doggosapp.ui.screens.ProfileScreen
import com.kacperkk.doggosapp.ui.screens.SettingsScreen
import com.kacperkk.doggosapp.ui.theme.DoggosAppTheme

class MainActivity : ComponentActivity() {
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

    NavHost(
        navController = navController,
        startDestination = "dog_list"
    ) {
        composable("dog_list") { DogListScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("dog_detail/{dogId}") { backStackEntry ->
            val dogId = backStackEntry.arguments?.getString("dogId") ?: "Unknown"
            DogDetailScreen(navController, dogId)
        }
    }
}