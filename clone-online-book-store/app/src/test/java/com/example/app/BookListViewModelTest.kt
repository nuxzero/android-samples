package com.example.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.app.data.NoteRepository
import com.example.app.data.models.Book
import com.example.app.ui.home.HomeViewModel
import com.example.app.utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class BookListViewModelTest {

    @get:Rule
    val instanceExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val noteRepository = mock(NoteRepository::class.java)
    private val observer = mock(Observer::class.java) as Observer<List<Book>>

    @Test
    fun `observe notes successful`() = runBlockingTest {
        val expectedNotes = emptyList<Book>()
        `when`(noteRepository.getNoteList()).thenReturn(flowOf(expectedNotes))
        val viewModel = HomeViewModel(noteRepository)

        val result = viewModel.books

        result.observeForever(observer)
        assertEquals(expectedNotes, result.value)
    }
}