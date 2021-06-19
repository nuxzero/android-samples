package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.AppTheme
import com.google.accompanist.glide.rememberGlidePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NetworkImage("https://picsum.photos/id/1020/800/800")
                }
            }
        }
    }
}

@Composable
fun NetworkImage(imageUrl: String) {
    Image(
        painter = rememberGlidePainter(
            request = imageUrl,
            previewPlaceholder = R.drawable.sample_image
        ),
        contentDescription = "Network image",
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        NetworkImage("Android")
    }
}
