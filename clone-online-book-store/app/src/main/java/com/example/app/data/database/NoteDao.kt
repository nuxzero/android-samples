package com.example.app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app.data.models.Book


@Dao
interface NoteDao {

    @Query("SELECT * FROM book")
    suspend fun getAll(): List<Book>

    @Query("SELECT * FROM book WHERE id == :id")
    suspend fun findById(id: Int): Book

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg books: Book)

    @Delete
    suspend fun delete(book: Book)
}
