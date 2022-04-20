package com.example.app

import android.view.KeyEvent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.ui.main.MainFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TimeEditTextUiTest {
    @Before
    fun setUp() {
        launchFragmentInContainer<MainFragment>()
    }

    @Test
    fun hourText_noInsertZeroIfFirstDigitLessThanTwo() {
        onView(withId(R.id.time_edit_text))
            .perform(typeText("1"))
            .check(matches(withText("1")))
    }

    @Test
    fun hourText_insertZeroAndAppendDelimiterIfFirstDigitMoreThanTwo() {
        onView(withId(R.id.time_edit_text))
            .perform(typeText("3"))
            .check(matches(withText("03:")))
    }

    @Test
    fun hourText_appendDelimiterIfHourTextLengthIsTwo() {
        onView(withId(R.id.time_edit_text))
            .perform(typeText("12"))
            .check(matches(withText("12:")))
    }

    @Test
    fun minuteText_notInsertZeroIfFirstDigitLessThanSix() {
        onView(withId(R.id.time_edit_text))
            .perform(typeText("1230"))
            .check(matches(withText("12:30")))
    }

    @Test
    fun minuteText_insertZeroIfFirstDigitMoreThanSix() {
        onView(withId(R.id.time_edit_text))
            .perform(typeText("126"))
            .check(matches(withText("12:06")))
    }

    @Test
    fun delimiter_notAbleToTypeDelimiter() {
        onView(withId(R.id.time_edit_text))
            .perform(typeText(":"))
            .check(matches(withText("")))
    }

    @Test
    fun delete_ableToDeleteSingleDigit() {
        onView(withId(R.id.time_edit_text))
            .perform(typeText(":"), pressKey(KeyEvent.KEYCODE_DEL))
            .check(matches(withText("")))
    }
}
