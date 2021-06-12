package com.example.app.ui.book_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.app.data.BooksRepository
import com.example.app.data.models.Book
import javax.inject.Inject

class BookDetailViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel() {
    private val bookId = MutableLiveData<Int>()
    val book: LiveData<Book> = bookId.switchMap { id ->
        repository.getNote(id).asLiveData()
    }

    fun setNoteId(id: Int) {
        bookId.value = id
    }
}
