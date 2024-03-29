package com.example.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.app.data.BooksRepository
import com.example.app.data.models.Book
import com.example.app.ui.book_detail.BookDetailViewModel
import com.example.app.utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.Date

@ExperimentalCoroutinesApi
class BookDetailViewModelTest {
    @get:Rule
    val instanceExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val noteRepository = mock(BooksRepository::class.java)
    private val observer = mock(Observer::class.java) as Observer<Book>

    @Test
    fun `observe note successful`() = runBlockingTest {
        val expectedNote = Book(
            id = 1,
            title = "Test",
            author = "John",
            createdAt = Date(),
            image = "Test",
            shortDescription = "Test note",
        )
        `when`(noteRepository.getNote(1)).thenReturn(flowOf(expectedNote))
        val viewModel = BookDetailViewModel(noteRepository)

        viewModel.setNoteId(1)

        val result = viewModel.book
        result.observeForever(observer)
        assertEquals(expectedNote, result.value)
    }
}