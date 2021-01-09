package me.cafecode.android.calculator;

import org.junit.Test;

public class DemoTestDoubleTest {

    @Test(expected = AddException.class)
    public void handle_exception() {

        // Dummy
        CalculatorListener dummyListener = new DummyCalculatorListener();

        // Stub
        AddCalculator stubCalculatorWithException = new StubCalculatorWithException();

        MainController mainController = new MainController();
        mainController.setCalculator(stubCalculatorWithException);  // Inject Calculator
        mainController.setListener(dummyListener);                  // Inject CalculatorListener
        mainController.add(3, 4);

    }

    class DummyCalculatorListener implements CalculatorListener {

        @Override
        public void onSuccess(String result) {

        }
    }

    class StubCalculatorWithException implements AddCalculator {

        @Override
        public double add(double firstOperand, double secondOperand) {
            throw new AddException();
        }
    }

}
