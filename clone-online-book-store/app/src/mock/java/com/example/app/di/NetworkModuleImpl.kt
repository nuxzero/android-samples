package com.example.app.di

import com.example.app.data.models.Book
import com.example.app.data.models.Profile
import com.example.app.data.network.NoteApi
import com.example.app.data.network.ProfileApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModuleImpl : NetworkModule {

    companion object {
        private const val HOST_NAME = "https://blooming-falls-95246.herokuapp.com/"
    }

    @Provides
    @Singleton
    override fun provideRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(HOST_NAME)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    override fun provideNoteApi(retrofit: Retrofit): NoteApi = object : NoteApi {
        override suspend fun getNewestBooks(): List<Book> {
            return MockData.getNewestBooks()
        }

        override suspend fun getPopularBooks(): List<Book> {
            return MockData.getPopularBooks()
        }

        override suspend fun getBook(id: Int): Book {
            return MockData.getNote(id)
        }
    }

    @Provides
    @Singleton
    override fun provideProfileApi(retrofit: Retrofit): ProfileApi = object : ProfileApi {
        override suspend fun getProfile(): Profile {
            return MockData.getProfile()
        }
    }
}
