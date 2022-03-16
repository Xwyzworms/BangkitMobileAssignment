package com.example.sec3_latihanaplikasi18_room.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sec3_latihanaplikasi18_room.database.Note
import com.example.sec3_latihanaplikasi18_room.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteAddUpdateViewModel(application : Application) :ViewModel() {

    private val mNoteRepository : NoteRepository = NoteRepository(application)

    fun insert(note : Note) {
        mNoteRepository.insert(note)
    }

     fun update(note : Note) {
         mNoteRepository.update(note)
    }

     fun delete(note : Note) {
         mNoteRepository.delete(note)
    }

}