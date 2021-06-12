package com.example.app.data

import com.example.app.data.database.ProfileDao
import com.example.app.data.models.Profile
import com.example.app.data.network.ProfileApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class ProfileRepositoryTest {

    private val profileApi: ProfileApi = mock(ProfileApi::class.java)
    private val profileDao: ProfileDao = mock(ProfileDao::class.java)
    private val repository = ProfileRepository(profileApi, profileDao)

    @Test
    fun `get profile successful`() = runBlockingTest {
        val expectedProfile = Profile(
            id = 1,
            email = "john@email.com",
            fullName = "John Doe",
            image = "test",
        )
        `when`(profileApi.getProfile()).thenReturn(expectedProfile)
        `when`(profileDao.get()).thenReturn(expectedProfile)

        val result = repository.getProfile().toList()

        assertEquals(2, result.size)
        assertEquals(expectedProfile, result.first())
        assertEquals(expectedProfile, result[1])
    }
}
