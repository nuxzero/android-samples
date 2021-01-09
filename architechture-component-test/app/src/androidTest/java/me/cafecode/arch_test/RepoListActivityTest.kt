package me.cafecode.arch_test

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.equalTo
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class RepoListActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(RepoListActivity::class.java)

    @get:Rule
    val executorRule = CountingTaskExecutorRule()

    @Before
    fun setUp() {
        /** This is important when using @{ Intents.intended() } in the test. */
        Intents.init()
    }

    @After
    fun tearDown() {
        /** Do not forget to call @{ Intents.release() } after call @{ Intents.init() }  */
        Intents.release()
    }

    @Test
    fun shouldIntendedRepoDetailActivity() {
        executorRule.drainTasks(10, TimeUnit.SECONDS)
        onView(allOf(withId(R.id.list))) // ViewAssertion
                .perform(actionOnItemAtPosition<RepoListAdapter.RepoViewHolder>(0, click()))  // ViewAction
        intended(allOf(
                hasComponent(RepoDetailActivity::class.java.name),
                hasExtras(allOf(
                        hasEntry(equalTo(RepoDetailActivity.EXTRA_REPO_ID), equalTo(1))))))
    }

}