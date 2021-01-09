package com.example.sslglide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mylibrary.PhotoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Glide.with(this)
//            .load("https://3ds.selftestplatform.com/images/ULCardScheme_extraHigh.png")
////            .load("https://www.dummies.com/wp-content/uploads/bulletjournal.jpg")
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
//            .skipMemoryCache(true)
//            .into(image)

        startActivity(Intent(this, PhotoActivity::class.java))
    }
}