package com.example.app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app.data.models.Profile


@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile LIMIT 1")
    suspend fun get(): Profile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}
