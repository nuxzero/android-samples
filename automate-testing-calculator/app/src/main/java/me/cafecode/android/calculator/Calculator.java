package me.cafecode.android.calculator;

interface Calculator {
    double add(double firstOperand, double secondOperand);
    double sub(double firstOperand, double secondOperand);
    double mul(double firstOperand, double secondOperand);
    double divide(double firstOperand, double secondOperand);
}

interface AddCalculator {
    double add(double firstOperand, double secondOperand);
}

class RealCalculator implements Calculator, AddCalculator {

    public double add(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }

    public double sub(double firstOperand, double secondOperand) {
        return firstOperand - secondOperand;
    }

    public double mul(double firstOperand, double secondOperand) {
        return firstOperand * secondOperand;
    }

    public double divide(double firstOperand, double secondOperand) {
        if (secondOperand == 0) {
            throw new DivideByZeroException();
        }
        return firstOperand / secondOperand;
    }

}
