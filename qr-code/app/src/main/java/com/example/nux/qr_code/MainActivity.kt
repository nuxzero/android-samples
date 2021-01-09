package com.example.nux.qr_code

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scanButton.setOnClickListener { startActivity(Intent(this@MainActivity, QrCodeScannerActivity::class.java)) }
    }
}
