package me.cafecode.android.calculator;

/**
 * Created by Natthawut Hemathulin on 5/12/16 AD.
 * Email: natthawut1991@gmail.com
 */
public class RealAddCalculator implements AddCalculator {
    @Override
    public double add(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }
}
