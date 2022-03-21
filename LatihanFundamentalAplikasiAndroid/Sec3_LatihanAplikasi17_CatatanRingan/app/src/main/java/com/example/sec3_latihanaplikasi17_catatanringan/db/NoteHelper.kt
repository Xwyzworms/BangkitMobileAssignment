package com.example.sec3_latihanaplikasi17_catatanringan.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.sec3_latihanaplikasi17_catatanringan.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.sec3_latihanaplikasi17_catatanringan.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException
import kotlin.jvm.Throws


class NoteHelper(context : Context) {

    private var databaseHelper : DatabaseHelper = DatabaseHelper(context)
    private lateinit var database : SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE : String = TABLE_NAME

        private var INSTANCE : NoteHelper? = null
        fun getInstance(context : Context) :NoteHelper =
            INSTANCE ?: synchronized(this) {
            INSTANCE ?: NoteHelper(context)
        }
    }



    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()


        if (database.isOpen) database.close()
    }

    fun queryAll() : Cursor {
        val query : String =
                "SELECT *" +
                "FROM $DATABASE_TABLE " +
                "ORDER BY $_ID ASC"
        return database.rawQuery(query, null)
    }

    fun queryById(id : String ) : Cursor {
        return database.rawQuery(
            "SELECT * FROM $DATABASE_TABLE WHERE $_ID='$id'", null
        )
    }

    fun insert(value : ContentValues? ) : Long {
        return database.insert(DATABASE_TABLE, null, value)
    }

    fun update(id : String, values : ContentValues) : Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun delete(id : String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

}