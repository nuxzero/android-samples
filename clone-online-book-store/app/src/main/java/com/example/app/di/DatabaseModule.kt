package com.example.app.di

import android.content.Context
import com.example.app.data.database.AppDatabase
import com.example.app.data.database.NoteDao
import com.example.app.data.database.ProfileDao

interface DatabaseModule {
    fun provideDatabase(applicationContext: Context): AppDatabase
    fun provideNoteDao(database: AppDatabase): NoteDao
    fun provideProfileDao(database: AppDatabase): ProfileDao
}
