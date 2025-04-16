package com.kacperkk.doggosapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class DogImage(
    val message: String,
    val status: String
)
