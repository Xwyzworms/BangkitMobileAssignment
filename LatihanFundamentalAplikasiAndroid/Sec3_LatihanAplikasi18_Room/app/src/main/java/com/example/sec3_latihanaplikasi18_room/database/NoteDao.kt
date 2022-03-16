package com.example.sec3_latihanaplikasi18_room.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Note)

    @Update
    suspend fun update(note : Note)

    @Delete
    suspend fun delete(note : Note)

    @Query("SELECT * FROM note ORDER BY id ASC")
    fun getAllNotes() : LiveData<List<Note>>

}