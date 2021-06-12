package com.example.app.data

import com.example.app.data.database.ProfileDao
import com.example.app.data.models.Profile
import com.example.app.data.network.ProfileApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApi,
    private val profileDao: ProfileDao,
) {

    fun getProfile(): Flow<Profile> = flow {
        val cachedProfile = profileDao.get()
        emit(cachedProfile)

        val networkProfile = profileApi.getProfile()
        emit(networkProfile)
        profileDao.insert(networkProfile)
    }
}
