package io.github.occultus73.newreporternotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.occultus73.newreporternotes.R
import io.github.occultus73.newreporternotes.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

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

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position))
                }
            }
        }

        fun bind(position: Int) {
            with(itemView) {
                val currentNote: Note = getItem(position)

                text_view_title.text = currentNote.noteTitle
                text_view_description.text = currentNote.noteDescription
                text_view_priority.text = currentNote.notePriority.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) = holder.bind(position)

    fun getNoteAt(position: Int): Note? {
        return getItem(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

interface OnItemClickListener {
    fun onItemClick(note: Note)
}