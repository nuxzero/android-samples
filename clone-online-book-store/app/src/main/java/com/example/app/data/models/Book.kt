package com.example.app.data.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date


@Entity
@Parcelize
data class Book(
    @PrimaryKey
    val id: Int,
    val title: String,
    val author: String,
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @DrawableRes
    val image: Int,
    val shortDescription: String,
) : Parcelable
