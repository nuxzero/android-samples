package me.cafecode.android.calculator;

public class MainController {

    private AddCalculator addCalculator;
    private Calculator calculator;

    private CalculatorListener mListener;

    public MainController() {
        addCalculator = new RealAddCalculator();
        calculator = new RealCalculator();
    }

    public void add(double firstOperand, double secondOperand) {
        mListener.onSuccess(String.valueOf(addCalculator.add(firstOperand, secondOperand)));
    }

    public void sub(double firstOperand, double secondOperand) {
        mListener.onSuccess(String.valueOf(calculator.sub(firstOperand, secondOperand)));
    }

    public void divide(double firstOperand, double secondOperand) {
        try {
            mListener.onSuccess(String.valueOf(calculator.divide(firstOperand, secondOperand)));
        } catch (DivideByZeroException e) {
            mListener.onSuccess("Error");
        }
    }

    public void mul(double firstOperand, double secondOperand) {
        mListener.onSuccess(String.valueOf(calculator.mul(firstOperand, secondOperand)));
    }

    public void setListener(CalculatorListener listener) {
        mListener = listener;
    }

    public void setCalculator(AddCalculator addCalculator) {
        this.addCalculator = addCalculator;
    }

}
