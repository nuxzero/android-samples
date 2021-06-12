package com.example.app.ui.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.util.Date

@BindingAdapter("android:srcUrl")
fun setImageUrl(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView)
        .load(imageUrl)
        .centerCrop()
        .into(imageView)
}

@BindingAdapter("android:dateText")
fun setDateText(textView: TextView, date: Date) {
    textView.text = DateFormat.getDateInstance().format(date)
}
