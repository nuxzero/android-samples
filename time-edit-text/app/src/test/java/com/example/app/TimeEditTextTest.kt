package com.example.app

import android.content.Context
import android.view.KeyEvent
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TimeEditTextTest {
    private val context = ApplicationProvider.getApplicationContext<Context>();
    private val timeEditText = TimeEditText(context)

    @Test
    fun hourText_noInsertZeroIfFirstDigitLesThanTwo() {
        timeEditText.onKeyDown(KeyEvent.KEYCODE_4, KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_4))
        assertEquals("1", timeEditText.text.toString())
    }

    @Test
    fun hourText_insertZeroAndAppendDelimiterIfFirstDigitMoreThanTwo() {
        timeEditText.onKeyDown(KeyEvent.KEYCODE_3, KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_3))
        assertEquals("03:", timeEditText.text.toString())
    }
}
