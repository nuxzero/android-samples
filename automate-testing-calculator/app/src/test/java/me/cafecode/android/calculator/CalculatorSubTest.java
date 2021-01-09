package me.cafecode.android.calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class CalculatorSubTest {

    RealCalculator calculator = new RealCalculator();
    private double firstOperand;
    private double secondOperand;
    private double expectedResult;

    public CalculatorSubTest(double firstOperand, double secondOperand, double expectedResult) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.expectedResult = expectedResult;
    }

    @Test
    public void sub() {
        String message = String.format("%f - %f", firstOperand, secondOperand);
        assertEquals(message, this.expectedResult, calculator.sub(firstOperand, secondOperand));
    }

    @Parameters
    public static List<Object[]> subData() {
        return Arrays.asList(new Object[][]{
                {1, 2, -1.0},
                {2, 1, 1.0},
                {8, 2, 6.0},
                {-1, 6, -7.0},
                {10, 3, 7.0},
                {5, 3, 2.0},
                {6, 7, -1.0},
        });
    }

}
