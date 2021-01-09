package me.cafecode.android.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void add() {
        // Arrange
        onView(withId(R.id.operand_one_edit_text))
                .perform(typeText("2"));

        onView(withId(R.id.operand_two_edit_text))
                .perform(typeText("1"));

        // Act
        onView(withId(R.id.operation_add_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText("3.0")));
    }

    @Test
    public void add_isEmptyInput() {
        // Act
        onView(withId(R.id.operation_add_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText(R.string.computationError)));
    }

    @Test
    public void sub() {
        // Arrange
        onView(withId(R.id.operand_one_edit_text))
                .perform(typeText("2"));

        onView(withId(R.id.operand_two_edit_text))
                .perform(typeText("1"));

        // Act
        onView(withId(R.id.operation_sub_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText("1.0")));
    }

    @Test
    public void sub_isEmptyInput() {
        // Act
        onView(withId(R.id.operation_sub_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText(R.string.computationError)));
    }

    @Test
    public void divide() {
        // Arrange
        onView(withId(R.id.operand_one_edit_text))
                .perform(typeText("6"));

        onView(withId(R.id.operand_two_edit_text))
                .perform(typeText("3"));

        // Act
        onView(withId(R.id.operation_div_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText("2.0")));
    }

    @Test
    public void divide_divideByZero() {
        // Arrange
        onView(withId(R.id.operand_one_edit_text))
                .perform(typeText("6"));

        onView(withId(R.id.operand_two_edit_text))
                .perform(typeText("0"));

        // Act
        onView(withId(R.id.operation_div_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText(R.string.computationError)));
    }

    @Test
    public void divide_isEmptyInput() {
        // Act
        onView(withId(R.id.operation_div_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText(R.string.computationError)));
    }

    @Test
    public void multi() {
        // Arrange
        onView(withId(R.id.operand_one_edit_text))
                .perform(typeText("6"));

        onView(withId(R.id.operand_two_edit_text))
                .perform(typeText("3"));

        // Act
        onView(withId(R.id.operation_mul_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText("18.0")));
    }

    @Test
    public void multi_isEmptyInput() {
        // Act
        onView(withId(R.id.operation_mul_button))
                .perform(click());

        // Assert
        onView(withId(R.id.operation_result_text_view))
                .check(matches(withText(R.string.computationError)));
    }

}