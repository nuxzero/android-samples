package com.example.app

import androidx.annotation.DrawableRes


data class Menu(val id: Int, @DrawableRes val thumbnail: Int) {
    companion object {
        val menus = listOf(
            Menu(1, R.drawable.thumb_1),
            Menu(2, R.drawable.thumb_2),
            Menu(3, R.drawable.thumb_3),
            Menu(4, R.drawable.thumb_4),
            Menu(5, R.drawable.thumb_5),
            Menu(6, R.drawable.thumb_6),
            Menu(7, R.drawable.thumb_7),
            Menu(8, R.drawable.thumb_8),
            Menu(9, R.drawable.thumb_9),
            Menu(10, R.drawable.thumb_10),
        )
    }
}
