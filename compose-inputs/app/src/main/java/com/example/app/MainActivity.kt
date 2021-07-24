package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    Column(modifier = Modifier.padding(16.dp)) {
        TextField("Email", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        TextField("Card Number", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            TextField(
                placeholder = "MM/YY",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField("CVC", modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField("Name", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        TextField("Country", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(text = "Pay €55.00", modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun TextField(placeholder: String = "", modifier: Modifier = Modifier) {
    var cardNumber: TextFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(16)))
    }

    OutlinedTextField(
        value = cardNumber,
        onValueChange = { cardNumber = it },
        placeholder = { Text(text = placeholder) },
        shape = MaterialTheme.shapes.medium,
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
        ),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, text: String) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
        ),
        onClick = { /*TODO*/ },
        modifier = modifier
            .height(56.dp),
    ) {
        Text(text = text, color = Color.White)
    }
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
