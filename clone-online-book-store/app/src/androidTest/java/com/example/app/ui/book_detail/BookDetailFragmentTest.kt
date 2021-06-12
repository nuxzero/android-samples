package com.example.app.ui.book_detail

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
import com.example.app.data.models.Book
import com.example.app.util.ViewModelFactory
import com.example.app.util.onAttached
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.Date


@RunWith(AndroidJUnit4::class)
class BookDetailFragmentTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var navHostController: TestNavHostController
    private val mockViewModelFactory = mock(ViewModelFactory::class.java)
    private val mockViewModel = mock(BookDetailViewModel::class.java)
    private val noteLiveData = MutableLiveData<Book>()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val testNote = Book(
        id = 1,
        title = "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        author = "John Doe",
        createdAt = Date(),
        image = "https://picsum.photos/id/486/1280/720",
        shortDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Duis ultricies lacus sed turpis tincidunt id aliquet risus feugiat. Morbi tincidunt augue interdum velit euismod in pellentesque massa. Donec pretium vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Tincidunt tortor aliquam nulla facilisi cras. Adipiscing at in tellus integer. Lorem donec massa sapien faucibus et molestie ac feugiat sed. Adipiscing elit ut aliquam purus sit amet luctus venenatis. Nisl nunc mi ipsum faucibus. A pellentesque sit amet porttitor. Mattis rhoncus urna neque viverra justo nec ultrices dui sapien.\\n\\nElementum nisi quis eleifend quam adipiscing vitae proin sagittis. Faucibus pulvinar elementum integer enim neque. Dapibus ultrices in iaculis nunc sed. Sit amet justo donec enim diam vulputate ut pharetra. Risus at ultrices mi tempus. Cursus in hac habitasse platea dictumst quisque sagittis purus sit. Lorem ipsum dolor sit amet consectetur adipiscing elit pellentesque. Imperdiet sed euismod nisi porta lorem mollis aliquam ut. Diam maecenas ultricies mi eget. Posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis. Non diam phasellus vestibulum lorem sed risus ultricies tristique. In aliquam sem fringilla ut morbi tincidunt augue interdum. Lorem sed risus ultricies tristique nulla. Purus semper eget duis at tellus at urna condimentum. Feugiat vivamus at augue eget arcu dictum."
    )

    @Before
    fun setUp() {
        `when`(mockViewModelFactory.create(BookDetailViewModel::class.java)).thenReturn(mockViewModel)
        `when`(mockViewModel.book).thenReturn(noteLiveData)

        // Set test NavHostController
        navHostController = TestNavHostController(context).apply {
            setGraph(R.navigation.nav_graph)
            setCurrentDestination(R.id.book_detail_fragment)
        }

        launchFragmentInContainer(fragmentArgs = NoteDetailFragmentArgs(testNote).toBundle(), themeResId = R.style.Theme_Templateapp) {
            BookDetailFragment().apply {
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
        noteLiveData.postValue(testNote)

        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText("Note"))))
    }

    @Test
    fun showNote_successful() {
        noteLiveData.postValue(testNote)

        onView(withId(R.id.title_text)).check(matches(withText("sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")))
        onView(withId(R.id.author_text)).check(matches(withText("John Doe")))
        onView(withId(R.id.description_text)).check(matches(withText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Duis ultricies lacus sed turpis tincidunt id aliquet risus feugiat. Morbi tincidunt augue interdum velit euismod in pellentesque massa. Donec pretium vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Tincidunt tortor aliquam nulla facilisi cras. Adipiscing at in tellus integer. Lorem donec massa sapien faucibus et molestie ac feugiat sed. Adipiscing elit ut aliquam purus sit amet luctus venenatis. Nisl nunc mi ipsum faucibus. A pellentesque sit amet porttitor. Mattis rhoncus urna neque viverra justo nec ultrices dui sapien.\\n\\nElementum nisi quis eleifend quam adipiscing vitae proin sagittis. Faucibus pulvinar elementum integer enim neque. Dapibus ultrices in iaculis nunc sed. Sit amet justo donec enim diam vulputate ut pharetra. Risus at ultrices mi tempus. Cursus in hac habitasse platea dictumst quisque sagittis purus sit. Lorem ipsum dolor sit amet consectetur adipiscing elit pellentesque. Imperdiet sed euismod nisi porta lorem mollis aliquam ut. Diam maecenas ultricies mi eget. Posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis. Non diam phasellus vestibulum lorem sed risus ultricies tristique. In aliquam sem fringilla ut morbi tincidunt augue interdum. Lorem sed risus ultricies tristique nulla. Purus semper eget duis at tellus at urna condimentum. Feugiat vivamus at augue eget arcu dictum.")))
    }
}
