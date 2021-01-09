package me.cafecode.codecoverage

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class CalculatorTest {
    private val calculator = Calculator()

    @Test
    fun `add() one plus one should equals two`() {
        val result = calculator.add(1,1)

        assertThat(result, `is`(2))
    }
}