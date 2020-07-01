package io.github.occultus73.newreporternotes.ui

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.occultus73.newreporternotes.model.Note
import java.lang.String

private val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> =
    object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteID == newItem.noteID
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteTitle == newItem.noteTitle &&
                    oldItem.noteDescription == newItem.noteDescription &&
                    oldItem.notePriority == newItem.notePriority
        }
    }


class NoteListAdapter : ListAdapter<Note, NoteListAdapter.NoteHolder>(DIFF_CALLBACK) {

    private lateinit var  listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote: Note = getItem(position)
        text_view_title.text = currentNote.noteTitle
        text_view_description.text = currentNote.noteDescription
        text_view_priority.text = String.valueOf(currentNote.notePriority)
    }

    fun getNoteAt(position: Int): Note? {
        return getItem(position)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}