package com.kacperkk.doggosapp.model

data class Dog(
    val id: Int,
    val name: String,
    val breed: String,
    val imageUrl: String? = null,
    val isFavorite: Boolean = false
)