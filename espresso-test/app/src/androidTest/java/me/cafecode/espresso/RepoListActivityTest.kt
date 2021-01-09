package me.cafecode.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import me.cafecode.espresso.RecyclerViewMatcher.Companion.withItemCount
import me.cafecode.espresso.RecyclerViewMatcher.Companion.withRecyclerView
import org.hamcrest.Matchers.equalTo
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RepoListActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(RepoListActivity::class.java)

    private lateinit var appIdlingResource: AppIdlingResource

    @Before
    fun setUp() {
        /** This is important when using @{ Intents.intended() } in the test. */
        Intents.init()

        // Register Idling resource
        appIdlingResource = activityRule.activity.appIdlingResource
        IdlingRegistry.getInstance().register(appIdlingResource)
    }

    @After
    fun tearDown() {
        /** Do not forget to call @{ Intents.release() } after call @{ Intents.init() }  */
        Intents.release()

        // Unregister un-necessary Idling resource
        IdlingRegistry.getInstance().unregister(appIdlingResource)
    }

    @Test
    fun shouldShowRepoList() {
        onView(withId(R.id.list)).check(matches(withItemCount(100)))

        onView(allOf(withId(R.id.list))) // ViewAssertion
                .perform(scrollToPosition<RepoListAdapter.RepoViewHolder>(10), click())  // ViewAction

        intended(hasComponent(RepoDetailActivity::class.java.name))
    }

    @Test
    fun shouldIntendedRepoDetailActivity() {
        onView(withId(R.id.list)).check(matches(withItemCount(100)))

        onView(allOf(withId(R.id.list))) // ViewAssertion
                .perform(scrollToPosition<RepoListAdapter.RepoViewHolder>(10), click())  // ViewAction

        intended(allOf(
                hasComponent(RepoDetailActivity::class.java.name),
                hasExtras(allOf(
                        hasEntry(equalTo(RepoDetailActivity.EXTRA_REPO_ID), equalTo(36))))))
    }

    @Test
    fun checkItemDisplay() {
        onView(withId(R.id.list))
                .perform(scrollToPosition<RepoListAdapter.RepoViewHolder>(19))

        onView(withRecyclerView(R.id.list, 19, R.id.repo_name))
                .check(matches(withText("jamesgolick/resource_controller")))
    }
}