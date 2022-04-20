package com.example.app

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType.TYPE_CLASS_DATETIME
import android.text.InputType.TYPE_DATETIME_VARIATION_TIME
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText


class TimeEditText : AppCompatEditText, TextWatcher {
    private var currentCursor = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    companion object {
        private const val TAG = "TimeEditText"
        private const val DELIMITER = ':'
    }

    init {
        filters = arrayOf(InputFilter.LengthFilter(5))
        inputType = TYPE_CLASS_DATETIME or TYPE_DATETIME_VARIATION_TIME
        addTextChangedListener(this)

        // Prevent type ':' by user
        setOnKeyListener { _, _, event ->
            event?.unicodeChar?.toChar() == DELIMITER
        }
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        currentCursor = selEnd
        text?.let {
            setSelection(it.length)
            return@let
        }
        super.onSelectionChanged(selStart, selEnd)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    override fun beforeTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {}

    override fun afterTextChanged(editable: Editable?) {
        editable?.let {
            if (it.length == 2 && it.last() != DELIMITER) it.append(DELIMITER)

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val char = event?.unicodeChar?.toChar() ?: return false
        val currentPosition = text?.length ?: 0

        Log.i(TAG, "onKeyDown: $char, currentPosition: $currentPosition")
        Log.i(TAG, "onKeyDown: hourText: $hourText, minText: $minuteText")

        if (keyCode == KeyEvent.KEYCODE_DEL && text?.lastOrNull() == DELIMITER) {
            text?.let { it.delete(it.lastIndex - 1, it.length) }
            return true
        }

//        if (keyCode != KeyEvent.KEYCODE_DEL) {
//            when (currentCursor) {
//                0, 1, 2 -> if (hourText.length >= 2) return true
//                3, 4, 5 -> if (minuteText.length >= 2) return true
//            }
//        }

        when (currentPosition) {
            0 -> {
                char.digitToIntOrNull()?.let {
                    if (it > 2) {
                        text?.append("0$it")
                        return true
                    }
                }
            }
            1 -> {
                text?.first()?.digitToIntOrNull()?.let {
                    char.digitToIntOrNull()?.let { value ->
                        if (it == 2 && value > 3) {
                            return true
                        }
                    }
                }
            }
            // position 2 is delimiter
            3 -> {
                char.digitToIntOrNull()?.let {
                    if (it >= 6) {
                        text?.append("0$char")
                        return true
                    }
                }
            }
            4 -> {

            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private val hourText: String get() = text?.let { it.split(DELIMITER).firstOrNull() ?: "" } ?: ""
    private val minuteText: String get() = text?.let { it.split(DELIMITER).getOrNull(1) ?: "" } ?: ""
}
