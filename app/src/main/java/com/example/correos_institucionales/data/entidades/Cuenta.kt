package com.example.correos_institucionales.data.entidades

data class Cuenta(
    val idCuenta: Int,
    val direccionEmail: String,
    val tipo: String,     // Ej: "correo", "plataforma", etc.
    val estado: String    // Ej: "Activa", "Suspendida", "Cerrada"
)
