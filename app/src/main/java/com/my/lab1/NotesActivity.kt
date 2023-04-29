package com.my.lab1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val backButton = findViewById<View>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this@NotesActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val addNotes = findViewById<View>(R.id.add_info_button)
        addNotes.setOnClickListener {
            val intent = Intent(this@NotesActivity, AddInfo::class.java)
            startActivity(intent)
        }

        val viewNotes = findViewById<View>(R.id.view_info_button)
        viewNotes.setOnClickListener {
            val intent = Intent(this@NotesActivity, ViewInfo::class.java)
            startActivity(intent)
        }

        val deleteNotes = findViewById<View>(R.id.delete_all_info_button)
        deleteNotes.setOnClickListener {
            val intent = Intent(this@NotesActivity, DeleteInfo::class.java)
            startActivity(intent)
        }

    }
}