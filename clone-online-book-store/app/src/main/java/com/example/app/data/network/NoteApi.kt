package com.example.app.data.network

import com.example.app.data.models.Book
import retrofit2.http.GET
import retrofit2.http.Path


interface NoteApi {
    @GET("notes")
    suspend fun getNewestBooks(): List<Book>

    @GET("notes")
    suspend fun getPopularBooks(): List<Book>

    @GET("notes/{id}")
    suspend fun getBook(@Path("id") id: Int): Book
}
