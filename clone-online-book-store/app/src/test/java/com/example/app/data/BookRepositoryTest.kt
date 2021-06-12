package com.example.app.data

import com.example.app.data.database.NoteDao
import com.example.app.data.models.Book
import com.example.app.data.network.NoteApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.Date


@ExperimentalCoroutinesApi
class BookRepositoryTest {

    private val noteApi: NoteApi = mock(NoteApi::class.java)
    private val noteDao: NoteDao = mock(NoteDao::class.java)
    private val repository = NoteRepository(noteApi, noteDao)

    @Test
    fun `get notes successful`() = runBlockingTest {
        val expectedNotes = emptyList<Book>()
        `when`(noteApi.getNoteList()).thenReturn(expectedNotes)
        `when`(noteDao.getAll()).thenReturn(expectedNotes)

        val result = repository.getNoteList().toList()

        assertEquals(2, result.size)
        assertEquals(expectedNotes, result.first())
        assertEquals(expectedNotes, result[1])
    }

    @Test
    fun `get note successful`() = runBlockingTest {
        val expectedNote = Book(
            id = 1,
            title = "Test",
            author = "John",
            createdAt = Date(),
            image = "Test",
            note = "Test note",
        )
        `when`(noteDao.findById(1)).thenReturn(expectedNote)
        `when`(noteApi.getNote(1)).thenReturn(expectedNote)


        val result = repository.getNote(1).toList()

        assertEquals(2, result.size)
        assertEquals(expectedNote, result.first())
        assertEquals(expectedNote, result[1])
    }
}
