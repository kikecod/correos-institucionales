package com.example.correos_institucionales.data.entidades

data class AsignacionDetalle(
    val idCuenta: Int,
    val ci: Int,
    val nombre: String,
    val direccionEmail: String,
    val tipo: String,
    val fechaAsignacion: String
)
