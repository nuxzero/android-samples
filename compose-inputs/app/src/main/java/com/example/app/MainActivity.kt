package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CreditCardForm()
                }
            }
        }
    }
}

@Composable
fun CreditCardForm() {
    Column() {
        TextField("Email")
        TextField("Card Number")
        Row {
            TextField("MM/YY")
            TextField("CVC")
        }
        TextField("Name")
        TextField("Country")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Pay €55.00")
        }
    }
}

@Composable
fun TextField(placeholder: String = "") {
    var cardNumber: TextFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(16)))
    }

    OutlinedTextField(
        value = cardNumber,
        onValueChange = { cardNumber = it },
        placeholder = { Text(text = placeholder) },
        shape = MaterialTheme.shapes.medium,
        maxLines = 1,
//        colors = TextFieldDefaults.out
    )
}

@Preview(showBackground = true)
@Composable
fun CreditCardFormPreview() {
    AppTheme {
        CreditCardForm()
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    AppTheme {
        TextField(placeholder = "Placeholder")
    }
}
