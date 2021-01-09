package co.cafecode.databindingsample

import android.app.Activity
import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    private val viewModel = mock(LoginViewModel::class.java)
    private val isLoginabled = MutableLiveData<Boolean>()

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as App
        app.registerActivityCreatedLifecycleCallback<MainActivity> {
            it.viewModel = viewModel
        }
        activityTestRule.launchActivity(null)
        `when`(viewModel.isLoginabled).thenReturn(isLoginabled)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun initUi() {
        onView(withId(R.id.email_input)).check(matches(withText("")))
        onView(withId(R.id.password_input)).check(matches(withText("")))

        onView(withId(R.id.login_button)).check(matches(not(isEnabled())))
        onView(withId(R.id.login_button)).check(matches(not(isClickable())))
    }

    @Test
    fun validInputCredential() {
        isLoginabled.postValue(true)

        onView(withId(R.id.login_button)).check(matches(isEnabled()))
        onView(withId(R.id.login_button)).check(matches(isClickable()))
    }

    @Test
    fun invalidInputCredential() {
        isLoginabled.postValue(false)

        onView(withId(R.id.login_button)).check(matches(not(isEnabled())))
        onView(withId(R.id.login_button)).check(matches(not(isClickable())))
    }
}

inline fun <reified T : Activity> Application.registerActivityCreatedLifecycleCallback(crossinline callback: (activity: T) -> Unit) {
    this.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(p0: Activity?) {}

        override fun onActivityResumed(p0: Activity?) {}

        override fun onActivityStarted(p0: Activity?) {}

        override fun onActivityDestroyed(p0: Activity?) {}

        override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {}

        override fun onActivityStopped(p0: Activity?) {}

        override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
            val activity = p0 as T
            callback(activity)
        }
    })
}