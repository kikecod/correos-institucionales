package com.example.correos_institucionales.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.correos_institucionales.data.entidades.Carrera
import com.example.correos_institucionales.data.database.Bdsqlite

class CarreraDao(private val context: Context) {

    fun insertar(carrera: Carrera): Boolean {
        val db = Bdsqlite(context).writableDatabase
        val values = ContentValues().apply {
            put("id_carrera", carrera.idCarrera)
            put("nombre", carrera.nombre)
            put("facultad", carrera.facultad)
            put("duracion_anios", carrera.duracionAnios)
        }
        val resultado = db.insert("CARRERA", null, values)
        db.close()
        return resultado != -1L
    }

    fun obtener(idCarrera: Int): Carrera? {
        val db = Bdsqlite(context).readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM CARRERA WHERE id_carrera = ?", arrayOf(idCarrera.toString()))
        var carrera: Carrera? = null
        if (cursor.moveToFirst()) {
            carrera = Carrera(
                idCarrera = cursor.getInt(0),
                nombre = cursor.getString(1),
                facultad = cursor.getString(2),
                duracionAnios = cursor.getInt(3)
            )
        }
        cursor.close()
        db.close()
        return carrera
    }
    fun actualizar(carrera: Carrera): Boolean {
        val db = Bdsqlite(context).writableDatabase
        val values = ContentValues().apply {
            put("nombre", carrera.nombre)
            put("facultad", carrera.facultad)
            put("duracion_anios", carrera.duracionAnios)
        }
        val filas = db.update("CARRERA", values, "id_carrera = ?", arrayOf(carrera.idCarrera.toString()))
        db.close()
        return filas > 0
    }

    fun eliminar(idCarrera: Int): Boolean {
        val db = Bdsqlite(context).writableDatabase
        val filas = db.delete("CARRERA", "id_carrera = ?", arrayOf(idCarrera.toString()))
        db.close()
        return filas > 0
    }
}