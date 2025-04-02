package com.kacperkk.doggosapp.model

data class Dog(
    val id: Int,
    val name: String,
    val breed: String,
    val isFavorite: Boolean = false
)