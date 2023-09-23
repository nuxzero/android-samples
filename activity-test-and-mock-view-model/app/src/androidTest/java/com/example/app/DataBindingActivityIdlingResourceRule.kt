package com.example.app

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description


/**
 * A JUnit rule that registers an idling resource for all fragment views that use data binding.
 *
 * @see [Original source](https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/androidTest/java/com/android/example/github/util/DataBindingIdlingResourceRule.kt)
 */
class DataBindingActivityIdlingResourceRule<F : Activity> : TestWatcher() {
    private val idlingResource = DataBindingActivityIdlingResource<F>()

    fun monitorActivity(scenario: ActivityScenario<F>) {
        idlingResource.monitorActivity(scenario)
    }

    override fun finished(description: Description) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }
}
