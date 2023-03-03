package com.example.new_task.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.BuildCompat
import androidx.core.os.BuildCompat.isAtLeastT
import com.example.new_task.R
import com.example.new_task.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener,
    OnCameraMoveListener, OnCameraIdleListener, OnCameraMoveStartedListener {

    companion object {
        private const val LOCATION_PERMISSION_CODE = 19
        private const val DEFAULT_ZOOM = 15f
        private const val ENTRIES = 5

    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermission = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        binding.etSearch.setOnClickListener {
            val location = binding.etSearch.text.toString()
            var addressList: List<Address>? = null
            val geocoder = Geocoder(this@MapsActivity)
            try {
                addressList = geocoder.getFromLocationName(location, ENTRIES)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address: Address? = addressList?.get(0)
            val latLng = LatLng(address!!.latitude, address.longitude)
            mMap.addMarker(MarkerOptions().position(latLng).title(location))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM))

        }
        binding.btnCancel.setOnClickListener {
            binding.etSearch.setText("")
        }

        //gpsLocation()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val mGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        Log.e(TAG, "onCreate: $mGps")
        if (mGps) {
            fetchLocation()
        } else {
            if (!mGps) {
                AlertDialog.Builder(this)
                    .setTitle("Turn On Location Mode")
                    .setMessage("This add Needs the  Current Location  ,Please access to use Location functionality")
                    .setPositiveButton("Ok") { _, _ ->
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(intent)
                    }
                    .create()
                    .show()

            }

        }


    }

    override fun onResume() {
        super.onResume()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val mGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (mGps) {
            fetchLocation()
        }
    }


        private fun fetchLocation() {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                ) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CODE
                )
                return
            } else {
                currentLocationMethod()
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            currentLocation = location
                            Toast.makeText(
                                applicationContext, currentLocation.latitude.toString() + "" +
                                        (currentLocation.longitude), Toast.LENGTH_SHORT
                            ).show()
                            val supportMapFragment =
                                (supportFragmentManager.findFragmentById(R.id.map) as
                                        SupportMapFragment?)!!
                            supportMapFragment.getMapAsync(this)
                        }

                    }
            }


        }

        private fun currentLocationMethod() {

        }

        override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
            super.onSaveInstanceState(outState)
        }

        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap

            val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
            Log.e(TAG, "onMapReady: $latLng")
            val marker = MarkerOptions().position(latLng).title("It's Mee Jeni")
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM))

            mMap.addMarker(marker)
            mMap.isMyLocationEnabled
            mMap.setOnCameraIdleListener(this)
            mMap.setOnCameraMoveStartedListener(this)


        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            locationPermission = false
            when (requestCode) {
                LOCATION_PERMISSION_CODE -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            //  locationPermission = true
                            fetchLocation()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Permission Denied1", Toast.LENGTH_SHORT)
                            .show()
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        ) {
                            AlertDialog.Builder(this)
                                .setTitle("Location Permission Needed")
                                .setMessage("This add Needs the Location Permission ,Please access to use Location functionality")
                                .setPositiveButton("Ok") { _, _ ->
                                    startActivity(
                                        Intent(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", this.packageName, null)
                                        )
                                    )
                                }
                                .create()
                                .show()

                        }

                    }

                }
            }
        }

        override fun onLocationChanged(location: Location) {
            var addressList: List<Address>? = null
            val geocoder: Geocoder = Geocoder(this@MapsActivity, Locale.getDefault())
            try {
                addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            setAddress(addressList!![0])


        }

        private fun setAddress(address: Address?) {
            if (address != null) {

                if (address.getAddressLine(0) != null) {
                    binding.etSearch.setText(address.getAddressLine(0))
                }
                if (address.getAddressLine(1) != null) {
                    binding.etSearch.setText(
                        binding.etSearch.text.toString() + address.getAddressLine(1)
                    )
                }
            }
        }

        override fun onCameraMove() {

        }

        override fun onCameraIdle() {
            var addressList: List<Address>? = null
            val geocoder: Geocoder = Geocoder(this@MapsActivity, Locale.getDefault())
            try {
                addressList = geocoder.getFromLocation(
                    mMap.cameraPosition.target.latitude,
                    mMap.cameraPosition.target.longitude,
                    1
                )
                setAddress(addressList!![0])
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }


        }

        override fun onCameraMoveStarted(p0: Int) {

        }
    }


