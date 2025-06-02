package com.example.correos_institucionales.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Bdsqlite(context: Context) : SQLiteOpenHelper(context, "correosUMSA", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE ESTUDIANTE (
                ci INTEGER PRIMARY KEY,
                nombre TEXT NOT NULL,
                ap_paterno TEXT NOT NULL,
                ap_materno TEXT NOT NULL,
                fecha_nac TEXT,
                sexo TEXT,
                telefono TEXT,
                email_personal TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE CUENTA (
                id_cuenta INTEGER PRIMARY KEY,
                direccion_email TEXT UNIQUE NOT NULL,
                tipo TEXT NOT NULL,
                estado TEXT NOT NULL
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE CARRERA (
                id_carrera INTEGER PRIMARY KEY,
                nombre TEXT NOT NULL,
                facultad TEXT NOT NULL,
                duracion_anios INTEGER NOT NULL
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE ASIGNA (
                id_cuenta INTEGER,
                ci INTEGER,
                fecha_asignacion TEXT NOT NULL,
                PRIMARY KEY(id_cuenta, ci),
                FOREIGN KEY(id_cuenta) REFERENCES CUENTA(id_cuenta),
                FOREIGN KEY(ci) REFERENCES ESTUDIANTE(ci)
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE INSCRIBE (
                ci INTEGER,
                id_carrera INTEGER,
                gestion TEXT NOT NULL,
                semestre TEXT NOT NULL,
                fecha_inscripcion TEXT NOT NULL,
                PRIMARY KEY(ci, id_carrera),
                FOREIGN KEY(ci) REFERENCES ESTUDIANTE(ci),
                FOREIGN KEY(id_carrera) REFERENCES CARRERA(id_carrera)
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS INSCRIBE")
        db.execSQL("DROP TABLE IF EXISTS ASIGNA")
        db.execSQL("DROP TABLE IF EXISTS CUENTA")
        db.execSQL("DROP TABLE IF EXISTS CARRERA")
        db.execSQL("DROP TABLE IF EXISTS ESTUDIANTE")
        onCreate(db)
    }
}