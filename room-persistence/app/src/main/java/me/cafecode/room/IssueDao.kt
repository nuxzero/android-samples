package me.cafecode.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable


@Dao
interface IssueDao {
    @Query("SELECT * FROM issue")
    fun getAll(): List<Issue>

    @Query("SELECT * FROM issue WHERE id = :id")
    fun get(id: Int): Issue

    @Insert
    fun insertAll(vararg issue: Issue)

    @Insert
    fun insertAll(issues: List<Issue>)

    // LiveData
    @Query("SELECT * FROM issue")
    fun getAllLiveData(): LiveData<List<Issue>>

    @Query("SELECT * FROM issue WHERE id = :id")
    fun getLiveData(id: Int): LiveData<Issue>

    // RxJava
    @Query("SELECT * FROM issue")
    fun getAllRx(): Flowable<List<Issue>>

    @Query("SELECT * FROM issue WHERE id = :id")
    fun getRx(id: Int): Flowable<Issue>
}
