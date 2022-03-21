package com.example.sec3_latihanaplikasi17_catatanringan.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME : String = "note"
            const val _ID : String = "_id"
            const val TITLE : String = "title"
            const val DESCRIPTION : String = "description"
            const val Date : String = "date"
        }
    }
}