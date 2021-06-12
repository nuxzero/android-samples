package com.example.app.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Profile(
    @PrimaryKey
    val id: Int,
    val email: String,
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    val fullName: String,
    val image: String,
)
