package com.example.app.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.app.R
import com.example.app.data.models.Book
import com.example.app.util.ViewModelFactory
import com.example.app.util.onAttached
import com.example.app.utils.itemCount
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.Date

@RunWith(AndroidJUnit4::class)
class BookListFragmentTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var navHostController: TestNavHostController
    private val mockViewModelFactory = mock(ViewModelFactory::class.java)
    private val mockViewModel = mock(HomeViewModel::class.java)
    private val notesLiveData = MutableLiveData<List<Book>>()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val testNotes = listOf(
        Book(
            id = 1,
            title = "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            author = "John Doe",
            createdAt = Date(),
            image = "https://picsum.photos/id/486/1280/720",
            shortDescription = ""
        ),
        Book(
            id = 2,
            title = "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            author = "John Doe",
            createdAt = Date(),
            image = "https://picsum.photos/id/486/1280/720",
            shortDescription = ""
        ),
        Book(
            id = 3,
            title = "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            author = "John Doe",
            createdAt = Date(),
            image = "https://picsum.photos/id/486/1280/720",
            shortDescription = ""
        ),
    )

    @Before
    fun setUp() {
        `when`(mockViewModelFactory.create(HomeViewModel::class.java)).thenReturn(mockViewModel)
        `when`(mockViewModel.newestBooks).thenReturn(notesLiveData)

        // Set test NavHostController
        navHostController = TestNavHostController(context).apply {
            setGraph(R.navigation.nav_graph)
            setCurrentDestination(R.id.home_fragment)
        }

        launchFragmentInContainer(themeResId = R.style.Theme_Templateapp) {
            HomeFragment().apply {
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
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText("Home"))))
    }

    @Test
    fun showNotes_successful() {
        notesLiveData.postValue(testNotes)

        onView(withId(R.id.note_list)).check(matches(isDisplayed()))
        onView(withId(R.id.note_list)).check(matches(itemCount(testNotes.size)))
    }

    @Test
    fun clickNoteItem_navigatesToNoteDetail() {
        notesLiveData.postValue(testNotes)

        onView(withId(R.id.note_list)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        assertEquals(R.id.book_detail_fragment, navHostController.currentDestination?.id)
        assertNotNull(navHostController.currentDestination!!.arguments["note"])
    }
}
