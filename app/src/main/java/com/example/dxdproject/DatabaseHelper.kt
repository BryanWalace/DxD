package com.example.dxdproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "atributos.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "atributos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_RACA = "raca"
        private const val COLUMN_FORCA = "forca"
        private const val COLUMN_DESTREZA = "destreza"
        private const val COLUMN_CONSTITUICAO = "constituicao"
        private const val COLUMN_INTELIGENCIA = "inteligencia"
        private const val COLUMN_SABEDORIA = "sabedoria"
        private const val COLUMN_CARISMA = "carisma"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_RACA TEXT," +
                "$COLUMN_FORCA INTEGER," +
                "$COLUMN_DESTREZA INTEGER," +
                "$COLUMN_CONSTITUICAO INTEGER," +
                "$COLUMN_INTELIGENCIA INTEGER," +
                "$COLUMN_SABEDORIA INTEGER," +
                "$COLUMN_CARISMA INTEGER)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun saveAttributes(raca: String, forca: Int, destreza: Int, constituicao: Int, inteligencia: Int, sabedoria: Int, carisma: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_RACA, raca)
        contentValues.put(COLUMN_FORCA, forca)
        contentValues.put(COLUMN_DESTREZA, destreza)
        contentValues.put(COLUMN_CONSTITUICAO, constituicao)
        contentValues.put(COLUMN_INTELIGENCIA, inteligencia)
        contentValues.put(COLUMN_SABEDORIA, sabedoria)
        contentValues.put(COLUMN_CARISMA, carisma)

        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun saveCharacter(raca: String, forca: Int, destreza: Int, constituicao: Int, inteligencia: Int, sabedoria: Int, carisma: Int, pontosRestantes: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_RACA, raca)
            put(COLUMN_FORCA, forca)
            put(COLUMN_DESTREZA, destreza)
            put(COLUMN_CONSTITUICAO, constituicao)
            put(COLUMN_INTELIGENCIA, inteligencia)
            put(COLUMN_SABEDORIA, sabedoria)
            put(COLUMN_CARISMA, carisma)
            // Você pode adicionar uma coluna para pontos restantes se quiser armazená-la
            // put(COLUMN_PONTOS_RESTANTES, pontosRestantes)
        }

        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }
}
