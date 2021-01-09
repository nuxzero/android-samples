package com.example.nux.qr_code

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.qr_code_scanner_activity.*

class QrCodeScannerActivity : AppCompatActivity() {

    private var cameraSource: CameraSource? = null
    private var barcode: BarcodeDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_code_scanner_activity)

        barcode = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()

        barcode?.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val barcode = detections?.detectedItems
                if (barcode?.size() == 0) {
                    return
                }
                Log.d("qr code", barcode?.valueAt(0)?.rawValue)
//                barcode?.valueAt(0)?.let {
//                    Toast.makeText(this@QrCodeScannerActivity, it.rawValue, Toast.LENGTH_LONG).show()
//                }
            }

        })
        cameraSource = CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(15.0f)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1080, 1920)
                .build()

        startCameraPreview()
    }

    @SuppressLint("MissingPermission")
    fun startCameraPreview() {
        cameraSourcePreview.start(cameraSource)
    }
}
