package me.cafecode.android.calculator;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class DemoControllerWithViewTest {

    @Test
    public void result_of_add_two_number_should_show_on_view() {
        AddCalculator calculator = new AddCalculator() {
            @Override
            public double add(double firstOperand, double secondOperand) {
                return 0;
            }
        };

        // Spy
        /*CalculatorListener spyCalculatorListener = new CalculatorListener() {
            public boolean called;

            @Override
            public void onSuccess(String result) {
                called = true;
            }

            public boolean verify() {
                return called;
            }
        };*/

        // Mock
        class MockCalculatorListener implements CalculatorListener {
            boolean called = false;

            @Override
            public void onSuccess(String result) {
                called = true;
            }

            public boolean verify() {
                return called;
            }
        }

        MockCalculatorListener mockCalculatorListener = new MockCalculatorListener();

        MainController mainController = new MainController();
        mainController.setCalculator(calculator);
        mainController.setListener(mockCalculatorListener);
        mainController.add(2, 1);
        assertTrue(mockCalculatorListener.verify());
    }

}
