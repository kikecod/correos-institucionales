package com.example.correos_institucionales.data.dao

import android.content.ContentValues
import android.content.Context
import com.example.correos_institucionales.data.entidades.Inscribe
import com.example.correos_institucionales.data.database.Bdsqlite

class InscribeDao(private val context: Context) {

    fun insertar(inscribe: Inscribe): Boolean {
        val db = Bdsqlite(context).writableDatabase
        val values = ContentValues().apply {
            put("ci", inscribe.ci)
            put("id_carrera", inscribe.idCarrera)
            put("gestion", inscribe.gestion)
            put("fecha_inscripcion", inscribe.fechaInscripcion)
        }
        val resultado = db.insert("INSCRIBE", null, values)
        db.close()
        return resultado != -1L
    }

    fun eliminar(ci: Int, idCarrera: Int): Boolean {
        val db = Bdsqlite(context).writableDatabase
        val filas = db.delete(
            "INSCRIBE",
            "ci = ? AND id_carrera = ?",
            arrayOf(ci.toString(), idCarrera.toString())
        )
        db.close()
        return filas > 0
    }
}