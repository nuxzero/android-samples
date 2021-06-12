package com.example.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.app.data.models.Book
import com.example.app.data.models.Profile


@Database(entities = [Book::class, Profile::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun profileDao(): ProfileDao
}
