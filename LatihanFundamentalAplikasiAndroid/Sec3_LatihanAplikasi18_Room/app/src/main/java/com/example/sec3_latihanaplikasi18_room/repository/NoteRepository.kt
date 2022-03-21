package com.example.sec3_latihanaplikasi18_room.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.sec3_latihanaplikasi18_room.database.Note
import com.example.sec3_latihanaplikasi18_room.database.NoteDao
import com.example.sec3_latihanaplikasi18_room.database.NoteRoomDatabase
import kotlinx.coroutines.*

class NoteRepository (application : Application){

    private val mNotesDao : NoteDao


    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<List<Note>> {
      return mNotesDao.getAllNotes()
    }

        fun insert(note: Note) {
            CoroutineScope(Dispatchers.IO).launch {
                mNotesDao.insert(note)
            }
        }

        fun delete(note: Note) {
            CoroutineScope(Dispatchers.IO).launch {
                mNotesDao.delete(note)
            }
        }

        fun update(note: Note) {
            CoroutineScope(Dispatchers.IO).launch {
                mNotesDao.update(note)
            }
        }


    }