package com.example.app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    companion object {
        private const val STATE_CURRENT_URL = "current_url"
    }

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)

        if (savedInstanceState == null) {
            webView.loadUrl("https://dev.to/")
        }

        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok) { _, _ -> result?.confirm() }
                    .setOnCancelListener { result?.cancel() }
                    .show()
                return true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save the user's current state.
        outState.run {
            putString(STATE_CURRENT_URL, webView.url)
        }

        // Always call the superclass so it can save the view hierarchy state.
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restore state members from saved instance
        savedInstanceState.getString(STATE_CURRENT_URL)?.let {
            webView.loadUrl(it)
        }
    }
}
