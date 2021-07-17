package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.Colors
import com.example.app.ui.theme.SampleAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        NormalTextField(modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(16.dp))
                        BorderedTextField(modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomTextField(modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomOutlinedTextField(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
fun NormalTextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        placeholder = { Text("Placeholder") },
        modifier = modifier,
        singleLine = true,
    )
}

@Composable
fun BorderedTextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        placeholder = { Text("Placeholder") },
        modifier = modifier,
        singleLine = true,
    )
}

@Composable
fun CustomTextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column {
        Text(
            text = "Label",
            style = TextStyle(
                fontSize = 13.sp,
                color = Colors.Primary,
            ),
            modifier = Modifier.padding(start = 16.dp)
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Placeholder") },
            modifier = modifier,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Colors.Text,
                focusedLabelColor = Colors.Primary,
                unfocusedLabelColor = Colors.Unfocus,
                unfocusedBorderColor = Colors.Unfocus,
                focusedBorderColor = Colors.Primary,
            ),
            shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
        )

    }
}

@Composable
fun CustomOutlinedTextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        placeholder = { Text("Placeholder") },
        modifier = modifier,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Colors.Text,
            focusedLabelColor = Colors.Primary,
            unfocusedLabelColor = Colors.Unfocus,
            unfocusedBorderColor = Colors.Unfocus,
            focusedBorderColor = Colors.Primary,
        ),
        shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)),
    )
}

@Preview(showBackground = true)
@Composable
fun NormalTextFieldPreview() {
    SampleAppTheme {
        NormalTextField()
    }
}

@Preview(showBackground = true)
@Composable
fun BorderedTextFieldPreview() {
    SampleAppTheme {
        BorderedTextField()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    SampleAppTheme {
        CustomTextField(
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomOutlinedTextFieldPreview() {
    SampleAppTheme {
        CustomOutlinedTextField()
    }
}

