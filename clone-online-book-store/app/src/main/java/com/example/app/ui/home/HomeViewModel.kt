package com.example.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.app.data.BooksRepository
import com.example.app.data.models.Book
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val noteRepository: BooksRepository,
) : ViewModel() {

    val popularBooks: LiveData<List<Book>> = noteRepository.getPopularBooks().asLiveData()

    val newestBooks: LiveData<List<Book>> = noteRepository.getNewestBooks().asLiveData()
}
