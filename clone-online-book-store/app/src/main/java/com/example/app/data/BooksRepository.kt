package com.example.app.data

import com.example.app.data.database.NoteDao
import com.example.app.data.models.Book
import com.example.app.data.network.NoteApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BooksRepository @Inject constructor(
    private val noteApi: NoteApi,
    private val noteDao: NoteDao,
) {
    fun getNewestBooks(): Flow<List<Book>> = flow {
        val cachedNotes = noteDao.getAll()
        emit(cachedNotes)

        val networkNotes = noteApi.getNewestBooks()
        emit(networkNotes)
        noteDao.insertAll(*networkNotes.toTypedArray())
    }

    fun getPopularBooks(): Flow<List<Book>> = flow {
        val cachedNotes = noteDao.getAll()
        emit(cachedNotes)

        val networkNotes = noteApi.getPopularBooks()
        emit(networkNotes)
        noteDao.insertAll(*networkNotes.toTypedArray())
    }

    fun getNote(id: Int): Flow<Book> = flow {
        emit(noteDao.findById(id))
        val note = noteApi.getBook(id)
        emit(note)
        noteDao.insertAll(note)
    }
}
