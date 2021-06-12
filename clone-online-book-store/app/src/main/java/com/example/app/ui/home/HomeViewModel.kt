package com.example.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.app.data.NoteRepository
import com.example.app.data.models.Book
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    val books: LiveData<List<Book>> = noteRepository.getNoteList().asLiveData()
}
