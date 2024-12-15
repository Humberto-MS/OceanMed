package com.example.oceanmed.data

data class RegistryData (
    val idRegistro: Int, // Añadir el ID del registro
    val fecha: String,
    val glucosa: Double,
    val presion_s: Int,
    val presion_d: Int
)
