package com.example.correos_institucionales.data.entidades

data class Estudiante(
    val ci: Int,
    val nombre: String,
    val apPaterno: String,
    val apMaterno: String,
    val fechaNac: String, // formato: YYYY-MM-DD
    val sexo: String,
    val telefono: String,
    val emailPersonal: String
)
