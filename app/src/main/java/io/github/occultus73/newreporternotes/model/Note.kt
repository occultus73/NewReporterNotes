package io.github.occultus73.newreporternotes.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var noteID : Int = 0,

    @ColumnInfo(name = "note_title")
    val noteTitle: String,

    @ColumnInfo(name = "note_description")
    val noteDescription: String,

    @ColumnInfo(name = "note_priority")
    val notePriority: Int
)