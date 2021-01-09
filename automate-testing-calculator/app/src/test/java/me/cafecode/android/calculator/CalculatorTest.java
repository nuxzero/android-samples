package me.cafecode.android.calculator;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CalculatorTest {

    RealCalculator calculator = new RealCalculator();

    @Test
    public void add_success() {
        // WHEN // Act
        // THEN // Assert
        assertEquals(2.0, calculator.add(1, 1));
    }

    @Test
    public void sub_success() {
        // Act
        // Assert
        assertEquals(0.0, calculator.sub(1, 1));
        assertEquals(1.0, calculator.sub(2, 1));
        assertEquals(-1.0, calculator.sub(1, 2));
    }

    @Test
    public void multiple_success() {
        // Act
        // Assert
        assertEquals(6.0, calculator.mul(3, 2));
    }

    @Test
    public void divide_success() {
        // Act
        // Assert
        assertEquals(3, calculator.divide(6, 2), 0);
    }

    @Test(expected = DivideByZeroException.class)
    public void divide_DivideByZeroException() {
        // Act
        double actualResult = calculator.divide(2, 0);
    }

}