package com.example.app

import org.junit.Assert.*
import org.junit.Test

class UserRepositoryTest {
    private val repository = UserRepository()

    @Test
    fun `getUser should return John Doe`() {
        val result = repository.getUser()
        assertEquals("John Doe", result)
    }
}
