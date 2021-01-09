package me.cafecode.android.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CalculatorListener {

    private TextView mResultText;
    private EditText mFirstOperandInput;
    private EditText mSecondOperandInput;
    private MainController mMainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultText = (TextView) findViewById(R.id.operation_result_text_view);
        mFirstOperandInput = (EditText) findViewById(R.id.operand_one_edit_text);
        mSecondOperandInput = (EditText) findViewById(R.id.operand_two_edit_text);

        // Injection code
        mMainController = new MainController();
        mMainController.setListener(this);

        Log.d("TAG", "Test");
    }

    public void onAdd(View view) {
        try {
            double firstOperand = Double.parseDouble(mFirstOperandInput.getText().toString());
            double secondOperand = Double.parseDouble(mSecondOperandInput.getText().toString());
            mMainController.add(firstOperand, secondOperand);
        } catch (NumberFormatException e) {
            mResultText.setText(R.string.computationError);
        }
    }

    public void onSub(View view) {
        try {
            double firstOperand = Double.parseDouble(mFirstOperandInput.getText().toString());
            double secondOperand = Double.parseDouble(mSecondOperandInput.getText().toString());
            mMainController.sub(firstOperand, secondOperand);
        } catch (NumberFormatException e) {
            mResultText.setText(R.string.computationError);
        }
    }

    public void onDiv(View view) {
        try {
            double firstOperand = Double.parseDouble(mFirstOperandInput.getText().toString());
            double secondOperand = Double.parseDouble(mSecondOperandInput.getText().toString());
            mMainController.divide(firstOperand, secondOperand);
        } catch (NumberFormatException e) {
            mResultText.setText(R.string.computationError);
        }
    }

    public void onMul(View view) {
        try {
            double firstOperand = Double.parseDouble(mFirstOperandInput.getText().toString());
            double secondOperand = Double.parseDouble(mSecondOperandInput.getText().toString());
            mMainController.mul(firstOperand, secondOperand);
        } catch (NumberFormatException e) {
            mResultText.setText(R.string.computationError);
        }
    }

    @Override
    public void onSuccess(String result) {
        mResultText.setText(result);
    }
}
