package com.example.app

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStreamReader


object ResourceFileReader {
    val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    fun readFile(path: String): String? {
        return try {
            val streamReader = InputStreamReader(this::class.java.getResourceAsStream(path))
            streamReader.readText()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    inline fun <reified T> fromFile(path: String): T? {
        val jsonString = readFile(path) ?: return null
        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return adapter.fromJson(jsonString)
    }

    inline fun <reified T> get(): T? {
        val objectPath = "/objects/${T::class.java.simpleName.camelToSnakeCase()}.json"
        return fromFile<T>(objectPath)
    }
}
