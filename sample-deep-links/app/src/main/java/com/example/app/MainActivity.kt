package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action = intent?.action
        val uri = intent?.data
        val query = uri?.getQueryParameter("message")

        val messageText = findViewById<TextView>(R.id.message_text)
        messageText.text = """
            action: $action
            uri: ${uri?.toString()}
            query: $query
        """.trimIndent()
    }
}