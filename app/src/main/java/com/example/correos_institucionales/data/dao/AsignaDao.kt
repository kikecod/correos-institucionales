package com.example.correos_institucionales.data.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.correos_institucionales.data.database.Bdsqlite
import com.example.correos_institucionales.data.entidades.Asigna
import com.example.correos_institucionales.data.entidades.AsignacionDetalle

class AsignaDao(private val bd: Bdsqlite) {

    fun insertar(asigna: Asigna): Boolean {
        val db = bd.writableDatabase
        val values = ContentValues().apply {
            put("id_cuenta", asigna.idCuenta)
            put("ci", asigna.ci)
            put("fecha_asignacion", asigna.fechaAsignacion)
        }
        val resultado = db.insert("ASIGNA", null, values)
        db.close()
        return resultado != -1L
    }

    fun actualizar(asigna: Asigna): Boolean {
        val db = bd.writableDatabase
        val values = ContentValues().apply {
            put("fecha_asignacion", asigna.fechaAsignacion)
        }
        val filas = db.update("ASIGNA", values, "id_cuenta = ? AND ci = ?", arrayOf(asigna.idCuenta.toString(), asigna.ci.toString()))
        db.close()
        return filas > 0
    }

    fun eliminar(idCuenta: Int, ci: Int): Boolean {
        val db = bd.writableDatabase
        val filas = db.delete("ASIGNA", "id_cuenta = ? AND ci = ?", arrayOf(idCuenta.toString(), ci.toString()))
        db.close()
        return filas > 0
    }

    fun obtenerTodos(): List<Asigna> {
        val db = bd.readableDatabase
        val lista = mutableListOf<Asigna>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM ASIGNA", null)
        if (cursor.moveToFirst()) {
            do {
                val fechaAsignacion = cursor.getString(cursor.getColumnIndexOrThrow("fecha_asignacion")) ?: ""
                val asigna = Asigna(
                    idCuenta = cursor.getInt(cursor.getColumnIndexOrThrow("id_cuenta")),
                    ci = cursor.getInt(cursor.getColumnIndexOrThrow("ci")),
                    fechaAsignacion = fechaAsignacion
                )
                lista.add(asigna)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }
    fun obtenerAsignacionesDetalladas(): List<AsignacionDetalle> {
        val db = bd.readableDatabase
        val lista = mutableListOf<AsignacionDetalle>()
        val query = """
        SELECT c.id_cuenta, e.ci, e.nombre, c.direccion_email, c.tipo, a.fecha_asignacion
        FROM ASIGNA a
        INNER JOIN ESTUDIANTE e ON e.ci = a.ci
        INNER JOIN CUENTA c ON c.id_cuenta = a.id_cuenta
        """
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            lista.add(
                AsignacionDetalle(
                    idCuenta = cursor.getInt(0),
                    ci = cursor.getInt(1),
                    nombre = cursor.getString(2),
                    direccionEmail = cursor.getString(3),
                    tipo = cursor.getString(4),
                    fechaAsignacion = cursor.getString(5)
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }
    fun existeAsignacionPorCi(ci: Int): Boolean {
        val db = bd.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM ASIGNA WHERE ci = ?", arrayOf(ci.toString()))
        val existe = cursor.moveToFirst() && cursor.getInt(0) > 0
        cursor.close()
        db.close()
        return existe
    }
}