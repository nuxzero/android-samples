package com.example.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.app.data.ProfileRepository
import com.example.app.data.models.Profile
import com.example.app.ui.account.AccountViewModel
import com.example.app.utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class AccountViewModelTest {
    @get:Rule
    val instanceExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val profileRepository = mock(ProfileRepository::class.java)
    private val observer = mock(Observer::class.java) as Observer<Profile>

    @Test
    fun `observe profile successful`() = runBlockingTest {
        val expectedProfile = Profile(
            id = 1,
            email = "john@email.com",
            fullName = "John Doe",
            image = "test",
        )
        `when`(profileRepository.getProfile()).thenReturn(flowOf(expectedProfile))
        val viewModel = AccountViewModel(profileRepository)

        val result = viewModel.profile

        result.observeForever(observer)
        assertEquals(expectedProfile, result.value)
    }
}
