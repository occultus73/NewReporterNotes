package io.github.occultus73.newreporternotes.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import io.github.occultus73.newreporternotes.model.db.NoteDatabase


class NoteRepository(context: Context) {

    val noteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "notes.db")
            .allowMainThreadQueries()
            .build()
    val noteDAO = noteDatabase.getNoteDAO()

    val allNotes: LiveData<List<Note>> = noteDAO.getAllNotes()

    fun insert(note: Note) = noteDAO.insert(note)
    fun update(note: Note) = noteDAO.update(note)
    fun delete(note: Note) = noteDAO.delete(note)
    fun deleteAllNotes() = noteDAO.deleteAllNotes()

}