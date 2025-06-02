package com.example.correos_institucionales.data.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.correos_institucionales.data.database.Bdsqlite
import com.example.correos_institucionales.data.entidades.Carrera

class CarreraDao(private val bd: Bdsqlite) {

    fun generarNuevoId(): Int {
        val db = bd.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT MAX(id_carrera) FROM CARRERA", null)
        var nuevoId = 1
        if (cursor.moveToFirst()) {
            val maxId = cursor.getInt(0)
            if (maxId > 0) nuevoId = maxId + 1
        }
        cursor.close()
        db.close()
        return nuevoId
    }

    fun insertar(carrera: Carrera): Boolean {
        val db = bd.writableDatabase
        val values = ContentValues().apply {
            put("id_carrera", carrera.idCarrera)
            put("nombre", carrera.nombre)
            put("facultad", carrera.facultad)
        }
        val resultado = db.insert("CARRERA", null, values)
        db.close()
        return resultado != -1L
    }

    fun actualizar(carrera: Carrera): Boolean {
        val db = bd.writableDatabase
        val values = ContentValues().apply {
            put("nombre", carrera.nombre)
            put("facultad", carrera.facultad)
        }
        val filas = db.update("CARRERA", values, "id_carrera = ?", arrayOf(carrera.idCarrera.toString()))
        db.close()
        return filas > 0
    }

    fun eliminar(idCarrera: Int): Boolean {
        val db = bd.writableDatabase
        val filas = db.delete("CARRERA", "id_carrera = ?", arrayOf(idCarrera.toString()))
        db.close()
        return filas > 0
    }

    fun obtenerTodos(): List<Carrera> {
        val db = bd.readableDatabase
        val lista = mutableListOf<Carrera>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM CARRERA", null)
        while (cursor.moveToNext()) {
            lista.add(
                Carrera(
                    idCarrera = cursor.getInt(0),
                    nombre = cursor.getString(1),
                    facultad = cursor.getString(2)
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }

    fun obtener(idCarrera: Int): Carrera? {
        val db = bd.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM CARRERA WHERE id_carrera = ?", arrayOf(idCarrera.toString()))
        var carrera: Carrera? = null
        if (cursor.moveToFirst()) {
            carrera = Carrera(
                idCarrera = cursor.getInt(0),
                nombre = cursor.getString(1),
                facultad = cursor.getString(2)
            )
        }
        cursor.close()
        db.close()
        return carrera
    }

}