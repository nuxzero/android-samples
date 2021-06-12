package com.example.app.data.network

import com.example.app.data.models.Profile
import retrofit2.http.GET


interface ProfileApi {
    @GET("profile")
    suspend fun getProfile(): Profile
}
