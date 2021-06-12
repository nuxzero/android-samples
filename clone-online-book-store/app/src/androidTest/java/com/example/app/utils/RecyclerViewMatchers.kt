package com.example.app.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

fun itemCount(itemCount: Int): BoundedMatcher<View, RecyclerView> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("Match item count: $itemCount")
        }

        override fun matchesSafely(item: RecyclerView?): Boolean {
            return item?.adapter?.itemCount == itemCount
        }
    }
