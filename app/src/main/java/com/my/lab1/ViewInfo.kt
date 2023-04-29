package com.my.lab1

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File


class ViewInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewinfo)

        val fileListView = findViewById<ListView>(R.id.file_list_view)
        val fileDirectory = Environment.getExternalStorageDirectory().toString()
        val fileList = File(fileDirectory).listFiles()

        val fileTitles = mutableListOf<String>()
        fileList?.forEach { file ->
            if (file.isFile) {
                fileTitles.add(file.name)
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileTitles)
        fileListView.adapter = adapter

        fileListView.setOnItemClickListener { parent, view, position, id ->
            val selectedFileTitle = parent.getItemAtPosition(position).toString()
            val filePath = "$fileDirectory/$selectedFileTitle"
            openFile(filePath)
        }
    }

    private fun openFile(filePath: String) {
        val packageName = applicationContext.packageName
        val file = File(filePath)
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = FileProvider.getUriForFile(this, packageName + ".provider", file)
            val mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
            intent.setDataAndType(uri, mime)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        }
    }
}
