package com.example.app

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var marker: Marker? = null
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            this.googleMap = it

            enableMyLocation()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap


        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {

            googleMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                // Got last known location. In some rare situations this can be null.
                Log.i("MainActivity", "${location.latitude},${location.longitude}")
                initialMarker(location)
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
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
                val targetPosition = this@MainActivity.googleMap.cameraPosition.target
                marker?.position = targetPosition
            }

            setOnCameraIdleListener {
                val targetPosition = this@MainActivity.googleMap.cameraPosition.target
                Log.i("MainActivity", "${targetPosition.latitude},${targetPosition.longitude}")
                // TODO: Get maps location from API
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Check if location permissions are granted and if so enable the
        // location data layer.
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation()
                }
        }
    }
}
