package com.example.correos_institucionales.data.entidades

data class Inscribe(
    val ci: Int,
    val idCarrera: Int,
    val gestion: String,           // Ej: "2024-1"
    val semestre: String,          // Ej: "1", "2"
    val fechaInscripcion: String   // formato: YYYY-MM-DD
)
