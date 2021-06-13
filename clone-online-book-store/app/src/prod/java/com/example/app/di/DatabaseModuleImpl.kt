package com.example.app.di

import android.content.Context
import androidx.room.Room
import com.example.app.data.database.AppDatabase
import com.example.app.data.database.NoteDao
import com.example.app.data.database.ProfileDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModuleImpl :DatabaseModule {
    private const val DATABASE_NAME = "note_database"

    @Provides
    @Singleton
    override fun provideDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    override fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    override fun provideProfileDao(database: AppDatabase): ProfileDao {
        return database.profileDao()
    }
}
