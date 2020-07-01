package io.github.occultus73.newreporternotes.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.occultus73.newreporternotes.model.Note

@Database(version = 1, entities = [Note::class])
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDAO(): NoteDAO
}