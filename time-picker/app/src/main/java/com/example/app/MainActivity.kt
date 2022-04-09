package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showTimePicker { hour, minute ->  }

        val fromTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 30)
        }

        val toTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 0)
        }

        val isValid = fromTime.before(toTime)
        Log.i("MainActivity", "is valid: $isValid")

        val time = "".split(":")
        val hour = time.getOrNull(0)?.toIntOrNull() ?: 0
        val minute = time.getOrNull(0)?.toIntOrNull() ?: 0

        Log.i("MainActivity", "parse hours: $hour; minute: $minute")
    }

    private fun showTimePicker(block: (hour: Int, minute: Int) -> Unit) {
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Set time")
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    block(hour, minute)
                }
            }
            .show(supportFragmentManager, null)
    }
}