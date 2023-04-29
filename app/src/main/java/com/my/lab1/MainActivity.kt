package com.my.lab1

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val notesButton = findViewById<View>(R.id.notes_button)
        notesButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NotesActivity::class.java)
            startActivity(intent)
        }

        val friendsButton = findViewById<View>(R.id.friends_button)
        friendsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, FriendsActivity::class.java)
            startActivity(intent)
        }

        val schoolButton = findViewById<View>(R.id.school_button)
        schoolButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SchoolActivity::class.java)
            startActivity(intent)
        }

        val closeButton = findViewById<View>(R.id.close_button)
        closeButton.setOnClickListener {
            finish()
        }

    }
}
