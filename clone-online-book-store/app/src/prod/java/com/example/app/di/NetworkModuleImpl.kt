package com.example.app.di

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
object NetworkModuleImpl : NetworkModule {

    private const val HOST_NAME = "https://blooming-falls-95246.herokuapp.com/"
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
    override fun provideNoteApi(retrofit: Retrofit): NoteApi {
        return retrofit.create(NoteApi::class.java)
    }

    @Provides
    override fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }
}
