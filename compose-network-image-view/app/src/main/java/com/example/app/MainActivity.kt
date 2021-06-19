package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NetworkImage("https://i.picsum.photos/id/1020/4288/2848.jpg?hmac=Jo3ofatg0fee3HGOliAIIkcg4KGXC8UOTO1dm5qIIPc")
                }
            }
        }
    }
}

@Composable
fun NetworkImage(imageUrl: String) {
    Text(
        text = imageUrl,
        style = TextStyle(
            color = Color.Red
        )
    )
//    Image(
//        painter = rememberGlidePainter(
//            request = imageUrl,
//            previewPlaceholder = R.drawable.sample_image,
//        ),
//        contentDescription = "Image description"
//    )
}

@Preview(showBackground = true)
@Composable
fun NetworkImagePreview() {
    AppTheme {
        NetworkImage("https://i.picsum.photos/id/1020/4288/2848.jpg?hmac=Jo3ofatg0fee3HGOliAIIkcg4KGXC8UOTO1dm5qIIPc")
    }
}
