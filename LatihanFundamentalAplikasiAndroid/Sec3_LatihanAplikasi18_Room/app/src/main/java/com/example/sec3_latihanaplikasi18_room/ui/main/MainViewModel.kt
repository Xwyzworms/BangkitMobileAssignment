package com.example.sec3_latihanaplikasi18_room.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sec3_latihanaplikasi18_room.database.Note
import com.example.sec3_latihanaplikasi18_room.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application : Application) : ViewModel(){

    private var _notes : LiveData<List<Note>> = MutableLiveData()
    private val notes get() = _notes
    private val mNoteRepository = NoteRepository(application)

    fun fetchNotes() {

         viewModelScope.launch {
             _notes = mNoteRepository.getAllNotes()
         }
    }

    fun getAllNotes() : LiveData<List<Note>> {
        return notes
    }
}