package com.my.lab1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class AddInfo : AppCompatActivity() {

    private lateinit var fileNameEditText: EditText
    private lateinit var fileContentEditText: EditText
    private lateinit var confirmButton: Button

    private val storagePermissionCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addinfo)

        fileNameEditText = findViewById(R.id.file_name)
        fileContentEditText = findViewById(R.id.file_content)
        confirmButton = findViewById(R.id.confirm_button)

        confirmButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    storagePermissionCode
                )
            } else {
                createFile()
            }
        }
    }

    private fun createFile() {
        val fileName = fileNameEditText.text.toString()
        val fileContent = fileContentEditText.text.toString()

        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState == Environment.MEDIA_MOUNTED) {
            val directory = getExternalFilesDir(null)
            val file = File(directory, fileName)
            try {
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(fileContent.toByteArray())
                fileOutputStream.close()
                Toast.makeText(this, "File saved to $directory/$fileName", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "External storage is not mounted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == storagePermissionCode && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            createFile()
        } else {
            Toast.makeText(
                this,
                "Storage permission is required to create the file",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
