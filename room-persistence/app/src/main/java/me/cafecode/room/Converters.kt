package me.cafecode.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*


class Converters {
    val gson: Gson by lazy {
        GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                .create()
    }

    @TypeConverter
    fun fromTimestamp(date: Date?): Long = date?.time ?: 0

    @TypeConverter
    fun toTimestamp(time: Long): Date = Date(time)

    @TypeConverter
    fun toListOfUsers(data: String): List<User>? = gson.fromJson<List<User>>(data, object : TypeToken<List<User>>() {}.type)

    @TypeConverter
    fun fromListOfUsers(list: List<User>?): String = gson.toJson(list, object : TypeToken<List<User>>() {}.type)
}