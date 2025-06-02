package com.example.correos_institucionales.data.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.correos_institucionales.data.database.Bdsqlite
import com.example.correos_institucionales.data.entidades.Estudiante

class EstudianteDao(private val bd: Bdsqlite) {

    fun insertar(estudiante: Estudiante): Boolean {
        val db = bd.writableDatabase
        val values = ContentValues().apply {
            put("ci", estudiante.ci)
            put("nombre", estudiante.nombre)
            put("ap_paterno", estudiante.apPaterno)
            put("ap_materno", estudiante.apMaterno)
            put("fecha_nac", estudiante.fechaNac)
            put("sexo", estudiante.sexo)
            put("telefono", estudiante.telefono)
            put("email_personal", estudiante.emailPersonal)
        }
        val resultado = db.insert("ESTUDIANTE", null, values)
        db.close()
        return resultado != -1L
    }

    fun obtener(ci: Int): Estudiante? {
        val db = bd.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ESTUDIANTE WHERE ci = ?", arrayOf(ci.toString()))
        var estudiante: Estudiante? = null
        if (cursor.moveToFirst()) {
            estudiante = Estudiante(
                ci = cursor.getInt(0),
                nombre = cursor.getString(1),
                apPaterno = cursor.getString(2),
                apMaterno = cursor.getString(3),
                fechaNac = cursor.getString(4),
                sexo = cursor.getString(5),
                telefono = cursor.getString(6),
                emailPersonal = cursor.getString(7)
            )
        }
        cursor.close()
        db.close()
        return estudiante
    }

    fun actualizar(estudiante: Estudiante): Boolean {
        val db = bd.writableDatabase
        val values = ContentValues().apply {
            put("nombre", estudiante.nombre)
            put("ap_paterno", estudiante.apPaterno)
            put("ap_materno", estudiante.apMaterno)
            put("fecha_nac", estudiante.fechaNac)
            put("sexo", estudiante.sexo)
            put("telefono", estudiante.telefono)
            put("email_personal", estudiante.emailPersonal)
        }
        val filas = db.update("ESTUDIANTE", values, "ci = ?", arrayOf(estudiante.ci.toString()))
        db.close()
        return filas > 0
    }

    fun eliminar(ci: Int): Boolean {
        val db = bd.writableDatabase
        val filas = db.delete("ESTUDIANTE", "ci = ?", arrayOf(ci.toString()))
        db.close()
        return filas > 0
    }
    fun obtenerTodos(): List<Estudiante> {
        val db = bd.writableDatabase
        val lista = mutableListOf<Estudiante>()
        val cursor = db.rawQuery("SELECT * FROM ESTUDIANTE", null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    Estudiante(
                        ci = cursor.getInt(0),
                        nombre = cursor.getString(1),
                        apPaterno = cursor.getString(2),
                        apMaterno = cursor.getString(3),
                        fechaNac = cursor.getString(4),
                        sexo = cursor.getString(5),
                        telefono = cursor.getString(6),
                        emailPersonal = cursor.getString(7)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }
}