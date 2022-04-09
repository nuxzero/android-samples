package com.example.app.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var marker: Marker? = null
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            this.googleMap = it

            enableMyLocation()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap


        enableMyLocation()
    }

    private val permissionLocationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            enableMyLocation()
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                // Got last known location. In some rare situations this can be null.
                Log.i("MainActivity", "${location?.latitude},${location?.longitude}")
                location?.let { initialMarker(it) }
            }
        } else {
            permissionLocationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun initialMarker(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions().apply {
            position(latLng)
        }
        marker = googleMap.addMarker(markerOptions)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f))
        this.googleMap.apply {
            setOnCameraMoveListener {
                val targetPosition = this@MainFragment.googleMap.cameraPosition.target
                marker?.position = targetPosition
            }

            setOnCameraIdleListener {
                val targetPosition = this@MainFragment.googleMap.cameraPosition.target
                Log.i("MainActivity", "${targetPosition.latitude},${targetPosition.longitude}")
                // TODO: Get maps location from API
            }
        }
    }
}
