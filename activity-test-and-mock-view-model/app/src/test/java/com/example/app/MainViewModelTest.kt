package com.example.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class MainViewModelTest {
    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    private val userRepository = mock<UserRepository>()

    @Test
    fun `loadUser should load user`() {
        val expectedUser = "John Doe"
        whenever(userRepository.getUser()).thenReturn(expectedUser)
        val viewModel = MainViewModel(userRepository)

        viewModel.loadUser()

        assertEquals(expectedUser, viewModel.user.value)
    }

    @Test
    fun `updateUser should update user`() {
        val expectedUser = "Elon Musk"
        whenever(userRepository.updateUser(expectedUser)).thenReturn(expectedUser)
        val viewModel = MainViewModel(userRepository)

        viewModel.updateUser()

        assertEquals(expectedUser, viewModel.user.value)
    }
}
