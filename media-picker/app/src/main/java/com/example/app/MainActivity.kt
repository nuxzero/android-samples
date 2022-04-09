package com.example.app

import android.app.Activity
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var chooseImageButton: Button
    private lateinit var chooseVideoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chooseImageButton = findViewById(R.id.chooseImageButton)
        chooseVideoButton = findViewById(R.id.chooseVideoButton)
        imageView = findViewById(R.id.image)

        chooseImageButton.setOnClickListener { openImagePicker() }
        chooseVideoButton.setOnClickListener { openVideoPicker() }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .galleryOnly()
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg",
                )
            )
            .cropSquare()
            .maxResultSize(800, 800)
            .compress(512)
            .createIntent { intent ->
                startForPickImageResult.launch(intent)
            }
    }

    private fun openVideoPicker() {
        ImagePicker.with(this)
            .galleryOnly()
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "video/mp4"
                )
            )
            .createIntent { intent ->
                startForPickVideoResult.launch(intent)
            }
    }

    private val startForPickImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            val uri = result.data?.data ?: return@registerForActivityResult

            val imageFile = uri.toFile()

            val thumbnailSize = Size(300, 300)
            val thumbFileName = "image_thumb_${System.currentTimeMillis()}.jpg"
            val thumbFile = File(cacheDir, thumbFileName)

            val thumbBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ThumbnailUtils.createImageThumbnail(
                    imageFile,
                    thumbnailSize,
                    CancellationSignal()
                )
            } else {
                ThumbnailUtils.createVideoThumbnail(
                    imageFile.path,
                    MediaStore.Images.Thumbnails.MINI_KIND
                )
            }

            val thumbOut = FileOutputStream(thumbFile)
            thumbBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, thumbOut)
            thumbOut.run {
                flush()
                close()
            }

            Log.i("MainActivity", thumbFile.toUri().toString())
        }

    private val startForPickVideoResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            val uri = result.data?.data ?: return@registerForActivityResult

            Log.i("MainActivity", uri.toString())
            val inputSteam = if (uri.scheme == "content") contentResolver.openInputStream(uri) else null

            val videoFileName = "video_${System.currentTimeMillis()}.mp4"
            val videoFile = File(cacheDir, videoFileName)
            val videoOut = FileOutputStream(videoFile)
            videoOut.write(inputSteam?.readBytes())
            videoOut.close()
            Log.i("MainActivity", videoFile.toUri().toString())

            val thumbnailSize = Size(300, 300)
            val thumbFileName = "video_thumb_${System.currentTimeMillis()}.jpg"
            val thumbFile = File(cacheDir, thumbFileName)

            val thumbBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ThumbnailUtils.createVideoThumbnail(
                    videoFile,
                    thumbnailSize,
                    CancellationSignal()
                )
            } else {
                ThumbnailUtils.createVideoThumbnail(
                    videoFile.path,
                    MediaStore.Images.Thumbnails.MINI_KIND
                )
            }

            val thumbOut = FileOutputStream(thumbFile)
            thumbBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, thumbOut);
            thumbOut.flush()
            thumbOut.close()
            Log.i("MainActivity", thumbFile.toUri().toString())

            val videoExtension = MimeTypeMap.getFileExtensionFromUrl(videoFile.path)
            val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(videoExtension);
            Log.i("MainActivity", "video: $type")

            val thumbExtension = MimeTypeMap.getFileExtensionFromUrl(thumbFile.path)
            val thumbType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(thumbExtension);
            Log.i("MainActivity", "thumbnail: $thumbType")

            imageView.setImageBitmap(thumbBitmap)
        }
}
