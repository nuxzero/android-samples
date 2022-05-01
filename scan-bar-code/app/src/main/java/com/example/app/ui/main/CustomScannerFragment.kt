package com.example.app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.DecodeHintType
import com.journeyapps.barcodescanner.*
import java.util.*

class CustomScannerFragment : Fragment(), BarcodeCallback {

    companion object {
        fun newInstance() = CustomScannerFragment()
    }

    private lateinit var viewModel: CustomScannerViewModel
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var capture: CaptureManager
    private lateinit var viewFinderView: ViewfinderView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.custom_scanner_fragment, container, false)
        barcodeScannerView = view.findViewById(R.id.zxing_barcode_scanner)
        viewFinderView = view.findViewById(R.id.zxing_viewfinder_view)

        barcodeScannerView.apply {
            decodeSingle(this@CustomScannerFragment)
            decoderFactory = DefaultDecoderFactory(listOf(BarcodeFormat.QR_CODE))
        }
        capture = CaptureManager(requireActivity(), barcodeScannerView).apply {
            setShowMissingCameraPermissionDialog(true)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CustomScannerViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun barcodeResult(result: BarcodeResult?) {
        result?.let {
            Log.i("CustomScannerFragment", "barcode result: ${it.text}")
            Toast.makeText(requireContext(), it.text, Toast.LENGTH_SHORT).show()
        }
    }
}
