package com.example.app.data.network

import com.example.app.data.models.Book
import retrofit2.http.GET
import retrofit2.http.Path


interface NoteApi {
    @GET("notes")
    suspend fun getNoteList(): List<Book>

    @GET("notes/{id}")
    suspend fun getNote(@Path("id") id: Int): Book
}
