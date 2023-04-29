package com.my.lab1

import android.content.ContentValues
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class AddInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addinfo)

        val fileName = findViewById<EditText>(R.id.file_name)
        val fileContent = findViewById<EditText>(R.id.file_content)
        val confirmButton = findViewById<Button>(R.id.confirm_button)

        // Get the content resolver
        val contentResolver = applicationContext.contentResolver

        // Set the MIME type for the text file
        val mimeType = "text/plain"

        // Create the ContentValues object with the file's metadata
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        }

        // Insert the file metadata into the MediaStore and get the file's URI
        val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        // Open an output stream to write the file contents to the MediaStore
        val outputStream = contentResolver.openOutputStream(uri!!)
        outputStream?.write(fileContent.toByteArray())
        outputStream?.close()

        // Print the file's URI
        Log.d("MainActivity", "File saved at: $uri")


        confirmButton.setOnClickListener {
            val fileNameString = fileName.text.toString()
            val fileContentString = fileContent.text.toString()

            val filePath = Environment.getExternalStorageDirectory().toString() + File.separator + fileNameString
            val file = File(filePath)
            val fos = FileOutputStream(file)
            fos.write(fileContentString.toByteArray())
            fos.close()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, do your work
            } else {
                // Permission denied, show a message or disable features that require permission
            }
        }
    }
    companion object {
        private const val PERMISSION_REQUEST_STORAGE = 1000
    }

}
