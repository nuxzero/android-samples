package com.example.app

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter


@BindingAdapter("app:src")
fun setImageResource(imageView: ImageView, @DrawableRes image: Int) {
    imageView.setImageResource(image)
}
