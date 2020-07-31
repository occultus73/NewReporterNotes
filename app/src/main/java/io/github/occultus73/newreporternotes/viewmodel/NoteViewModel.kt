package io.github.occultus73.newreporternotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.occultus73.newreporternotes.model.Note

import io.github.occultus73.newreporternotes.model.NoteRepository
import kotlinx.coroutines.GlobalScope


class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository = NoteRepository(application)

    val allNotes: LiveData<List<Note>> = repository.allNotes

    fun insert(note: Note) = repository.insert(note)
    fun update(note: Note) = repository.update(note)
    fun delete(note: Note) = repository.delete(note)
    fun deleteAllNotes() = repository.deleteAllNotes()
}