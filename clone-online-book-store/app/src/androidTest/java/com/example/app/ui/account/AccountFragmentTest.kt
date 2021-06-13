package com.example.app.ui.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.app.R
import com.example.app.data.models.Profile
import com.example.app.util.ViewModelFactory
import com.example.app.util.onAttached
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class AccountFragmentTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var navHostController: TestNavHostController
    private val mockViewModelFactory = mock(ViewModelFactory::class.java)
    private val mockViewModel = mock(AccountViewModel::class.java)
    private val profileLiveData = MutableLiveData<Profile>()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val testProfile = Profile(
        id = 1,
        fullName = "John Doe",
        email = "john@mail.com",
        image = "https://picsum.photos/id/433/300/300",
    )

    @Before
    fun setUp() {
        `when`(mockViewModelFactory.create(AccountViewModel::class.java)).thenReturn(mockViewModel)
        `when`(mockViewModel.profile).thenReturn(profileLiveData)

        // Set test NavHostController
        navHostController = TestNavHostController(context).apply {
            setGraph(R.navigation.nav_graph)
            setCurrentDestination(R.id.account_fragment)
        }

        launchFragmentInContainer(themeResId = R.style.Theme_Templateapp) {
            AccountFragment().apply {
                onAttached {
                    viewModelFactory = mockViewModelFactory
                }

                viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(requireView(), navHostController)
                    }
                }
            }
        }
    }

    @Test
    fun toolbar() {
        onView(withId(R.id.toolbar))
            .check(matches(hasDescendant(withText("Account"))))
    }

    @Test
    fun showProfile_successful() {
        profileLiveData.postValue(testProfile)

        onView(withId(R.id.full_name_text)).check(matches(withText("John Doe")))
        onView(withId(R.id.email_text)).check(matches(withText("john@mail.com")))
    }
}
