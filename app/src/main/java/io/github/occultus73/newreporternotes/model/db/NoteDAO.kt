package io.github.occultus73.newreporternotes.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.occultus73.newreporternotes.model.Note


@Dao
interface NoteDAO {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY note_priority ASC")
    fun getAllNotes(): LiveData<List<Note>>
}