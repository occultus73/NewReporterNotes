package io.github.occultus73.newreporternotes.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.occultus73.newreporternotes.R
import kotlinx.android.synthetic.main.activity_add_note.*

const val EXTRA_ID = "EXTRA_ID"
const val EXTRA_TITLE = "EXTRA_TITLE"
const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
const val EXTRA_PRIORITY = "EXTRA_PRIORITY"

class AddEditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            edit_text_title.setText(intent.getStringExtra(EXTRA_TITLE))
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Note"
        }
    }

    private fun saveNote() {
        val title = edit_text_title.text.toString().trim()
        val description = edit_text_description.text.toString().trim()
        val priority = number_picker_priority.value
        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent().apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_DESCRIPTION, description)
            putExtra(EXTRA_PRIORITY, priority)
        }

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}