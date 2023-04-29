package com.my.lab1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class FriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        
        val backButton = findViewById<View>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this@FriendsActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}