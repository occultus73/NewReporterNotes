package io.github.occultus73.newreporternotes.ui

import android.app.Activity
import android.app.Notification.EXTRA_TITLE
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.occultus73.newreporternotes.R
import io.github.occultus73.newreporternotes.model.Note
import io.github.occultus73.newreporternotes.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val ADD_NOTE_REQUEST = 1
    val EDIT_NOTE_REQUEST = 2

    private var noteViewModel: NoteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //add listener on add note button
        button_add_note.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        //setup recyclerview
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val adapter = NoteListAdapter()
        recyclerView.adapter = adapter

        //setup noteViewModel
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer<List<Note>> { notes -> adapter.submitList(notes) })

        //setup swipe functionality
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.getNoteAt(viewHolder.adapterPosition)?.let { noteViewModel.delete(it) }
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }


        }).attachToRecyclerView(recyclerView)
        adapter.setOnItemClickListener(object : NoteListAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                intent.putExtra(EXTRA_ID, note.noteID)
                intent.putExtra(EXTRA_TITLE, note.noteTitle)
                intent.putExtra(EXTRA_DESCRIPTION, note.noteDescription)
                intent.putExtra(EXTRA_PRIORITY, note.notePriority)
                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title = data.getStringExtra(EXTRA_TITLE)
            val description = data.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(EXTRA_PRIORITY, 1)

            val note = Note(noteTitle = title, noteDescription = description, notePriority = priority)

            noteViewModel.insert(note)
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()


        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data.getIntExtra(EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                return
            }

            val title = data.getStringExtra(EXTRA_TITLE)
            val description = data.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(EXTRA_PRIORITY, 1)

            val note = Note(noteTitle = title, noteDescription = description, notePriority = priority)

            note.noteID= id
            noteViewModel.update(note)
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}