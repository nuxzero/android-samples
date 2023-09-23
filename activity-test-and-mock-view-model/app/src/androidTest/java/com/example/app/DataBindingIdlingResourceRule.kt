package com.example.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description


/**
 * A JUnit rule that registers an idling resource for all fragment views that use data binding.
 *
 * @see [Original source](https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/androidTest/java/com/android/example/github/util/DataBindingIdlingResourceRule.kt)
 */
class DataBindingIdlingResourceRule<F : Fragment> : TestWatcher() {
    private val idlingResource = DataBindingIdlingResource<F>()

    fun monitorFragment(fragmentScenario: FragmentScenario<F>) {
        idlingResource.monitorFragment(fragmentScenario)
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
