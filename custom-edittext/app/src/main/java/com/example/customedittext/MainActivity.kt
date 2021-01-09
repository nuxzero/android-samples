package com.example.customedittext

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textBox = BorderedEditText(this).apply {
            val newLayoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 140)
            newLayoutParams.setMargins(20, 20, 20, 20)
            layoutParams = newLayoutParams
        }
        container.addView(textBox)

        AppCompatEditText(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            setText("input 1")
            setBackgroundColor(Color.BLUE)
            container.addView(this)
        }
    }
}