package io.github.occultus73.newreporternotes.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import io.github.occultus73.newreporternotes.model.db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


class NoteRepository(context: Context) {

    val noteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, "notes.db").build()
    val noteDAO = noteDatabase.getNoteDAO()

    //TODO: replace runBlocking clauses with a proper project-wide coroutine implementation
    val allNotes: LiveData<List<Note>> = runBlocking(Dispatchers.IO) { noteDAO.getAllNotes() }

    fun insert(note: Note) = runBlocking(Dispatchers.IO) { noteDAO.insert(note) }
    fun update(note: Note) =  runBlocking(Dispatchers.IO) { noteDAO.update(note) }
    fun delete(note: Note) = runBlocking(Dispatchers.IO) { noteDAO.delete(note) }
    fun deleteAllNotes() = runBlocking(Dispatchers.IO) { noteDAO.deleteAllNotes() }

}