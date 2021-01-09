package com.example.customedittext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText

internal class BorderedEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int =  R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyle) {

    init {
        setBackgroundColor(Color.BLUE)
    }
}