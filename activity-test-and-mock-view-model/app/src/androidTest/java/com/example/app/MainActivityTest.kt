package com.example.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    val idlingResourceRule = DataBindingActivityIdlingResourceRule<MainActivity>()

    private val mockViewModel = mock<MainViewModel>()
    private val user = MutableLiveData<String>()

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return mockViewModel as T
        }
    }

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        whenever(mockViewModel.user).thenReturn(user)
        doNothing().whenever(mockViewModel).loadUser()
        doNothing().whenever(mockViewModel).updateUser()

        // Intercept the activity creation and set the ViewModelProvider.Factory with the mock ViewModel
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback { activity, stage ->
            if (stage == Stage.PRE_ON_CREATE) {
                (activity as? MainActivity)?.setViewModelFactory(viewModelFactory)
            }
        }

        scenario = launch(MainActivity::class.java)

        // Monitor the activity so we can wait for the data binding to be ready
        idlingResourceRule.monitorActivity(scenario)
    }

    @Test
    fun displayUser_shouldDisplayUserName() {
        user.postValue("John Doe")
        onView(withId(R.id.user_name_text)).check(matches(withText("John Doe")))
    }

    @Test
    fun updateUser_shouldUpdateUserWhenPressUpdateButton() {
        user.postValue("Jane Doe")
        onView(withId(R.id.update_user_button)).perform(click())
        verify(mockViewModel).updateUser()
    }
}
