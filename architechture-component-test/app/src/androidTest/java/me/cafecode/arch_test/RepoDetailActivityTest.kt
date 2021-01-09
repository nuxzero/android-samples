package me.cafecode.arch_test

import android.content.Intent
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RepoDetailActivityTest {

    @get:Rule
    val intentRule = object : IntentsTestRule<RepoDetailActivity>(RepoDetailActivity::class.java) {
        override fun getActivityIntent(): Intent {
            return Intent().apply {
                putExtra(RepoDetailActivity.EXTRA_REPO_ID, 1)
            }
        }
    }

    @Rule
    @JvmField
    val executorRule = CountingTaskExecutorRule()

    private lateinit var appIdlingResource: AppIdlingResource

    @Before
    fun setUp() {
        // Register Idling resource
//        appIdlingResource = intentRule.activity.appIdlingResource!!
//        IdlingRegistry.getInstance().register(appIdlingResource)
    }

    @After
    fun tearDown() {
        // Unregister un-necessary Idling resource
//        IdlingRegistry.getInstance().unregister(appIdlingResource)
    }

    @Test
    fun shouldShowRepoDetail() {
        executorRule.drainTasks(10, TimeUnit.SECONDS)
        onView(withId(R.id.repo_name)).check(matches(withText("mojombo/grit")))
        onView(withId(R.id.owner_name)).check(matches(withText("mojombo")))
        onView(withId(R.id.repo_description)).check(matches(withText("**Grit is no longer maintained. Check out libgit2/rugged.** Grit gives you object oriented read/write access to Git repositories via Ruby.")))
    }


}
