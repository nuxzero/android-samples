package me.cafecode.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DependecyInjection {

    init {

    }

    fun createGson(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                .create()
    }

    fun createRetrofit(gson: Gson) : Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com/")
                .build()
    }
}
