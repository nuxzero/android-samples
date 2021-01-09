package me.cafecode.svg_image

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val IMAGE_URL = "https://media.flaticon.com/img/working.svg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestBuilder = GlideApp.with(this)
            .`as`(PictureDrawable::class.java)
            .listener(SvgSoftwareLayerSetter())

        requestBuilder.load(IMAGE_URL).into(image)
    }
}
