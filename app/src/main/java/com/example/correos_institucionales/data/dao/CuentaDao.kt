package com.example.correos_institucionales.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.correos_institucionales.data.entidades.Cuenta
import com.example.correos_institucionales.data.database.Bdsqlite

class CuentaDao(private val context: Context) {

    fun insertar(cuenta: Cuenta): Boolean {
        val db = Bdsqlite(context).writableDatabase
        val values = ContentValues().apply {
            put("id_cuenta", cuenta.idCuenta)
            put("direccion_email", cuenta.direccionEmail)
            put("tipo", cuenta.tipo)
            put("estado", cuenta.estado)
        }
        val resultado = db.insert("CUENTA", null, values)
        db.close()
        return resultado != -1L
    }

    fun obtener(idCuenta: Int): Cuenta? {
        val db = Bdsqlite(context).readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM CUENTA WHERE id_cuenta = ?", arrayOf(idCuenta.toString()))
        var cuenta: Cuenta? = null
        if (cursor.moveToFirst()) {
            cuenta = Cuenta(
                idCuenta = cursor.getInt(0),
                direccionEmail = cursor.getString(1),
                tipo = cursor.getString(2),
                estado = cursor.getString(3)
            )
        }
        cursor.close()
        db.close()
        return cuenta
    }

    fun actualizar(cuenta: Cuenta): Boolean {
        val db = Bdsqlite(context).writableDatabase
        val values = ContentValues().apply {
            put("direccion_email", cuenta.direccionEmail)
            put("tipo", cuenta.tipo)
            put("estado", cuenta.estado)
        }
        val filas = db.update("CUENTA", values, "id_cuenta = ?", arrayOf(cuenta.idCuenta.toString()))
        db.close()
        return filas > 0
    }

    fun eliminar(idCuenta: Int): Boolean {
        val db = Bdsqlite(context).writableDatabase
        val filas = db.delete("CUENTA", "id_cuenta = ?", arrayOf(idCuenta.toString()))
        db.close()
        return filas > 0
    }

    fun existeCorreo(correo: String): Boolean {
        val db = Bdsqlite(context).readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM CUENTA WHERE direccion_email = ?", arrayOf(correo))
        var existe = false
        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0
        }
        cursor.close()
        db.close()
        return existe
    }

    fun generarNuevoId(): Int {
        val db = Bdsqlite(context).readableDatabase
        val cursor = db.rawQuery("SELECT MAX(id_cuenta) FROM CUENTA", null)
        var nuevoId = 1
        if (cursor.moveToFirst()) {
            val maxId = cursor.getInt(0)
            if (maxId > 0) nuevoId = maxId + 1
        }
        cursor.close()
        db.close()
        return nuevoId
    }
    fun existeCi(ci: Int): Boolean {
        val db = Bdsqlite(context).readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM CUENTA c INNER JOIN ASIGNA a ON c.id_cuenta = a.id_cuenta WHERE a.ci = ?", arrayOf(ci.toString()))
        val existe = cursor.moveToFirst() && cursor.getInt(0) > 0
        cursor.close()
        db.close()
        return existe
    }
}