package com.example.customactionbarfont

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.TypefaceSpan
import android.view.Menu
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val menu1 = menu?.findItem(R.id.menu1)
        val titleSpannable = SpannableStringBuilder("Menu").apply {
            setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            setSpan(AbsoluteSizeSpan(20, true), 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        menu1?.title = titleSpannable

        return super.onCreateOptionsMenu(menu)
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        val menu1 = menu?.findItem(R.id.menu1)
//        val titleSpannable = SpannableStringBuilder("Menu").apply {
//            setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//            setSpan(AbsoluteSizeSpan(20, true), 0, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//        }
//        menu1?.title = titleSpannable
//        return super.onPrepareOptionsMenu(menu)
//    }
}
