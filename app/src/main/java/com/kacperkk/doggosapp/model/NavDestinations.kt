package com.kacperkk.doggosapp.model

import kotlinx.serialization.Serializable

@Serializable
object DogListScreen

@Serializable
object ProfileScreen

@Serializable
object SettingsScreen

@Serializable
object AddDogScreen

@Serializable
data class DogDetailScreen(val dogId: Int)