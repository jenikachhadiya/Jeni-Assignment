package com.example.new_task.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.new_task.BuildConfig
import com.example.new_task.R
import com.example.new_task.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val LOCATION_PERMISSION_CODE = 109
        private const val ACCESS_COARSE_LOCATION = 105
        private const val ACCESS_BACKGROUND_LOCATION = 110
    }


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var currentLocation: Location? = null
    private var cameraPosition: CameraPosition? = null


    //Api
    private lateinit var placesClients: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    //location rec by fusedLocationProvider
    private var lastLocation: Location? = null
    private var placeName: Array<String?> = arrayOfNulls(0)
    private var placeAddress: Array<String?> = arrayOfNulls(0)
    private var latLng: Array<LatLng?> = arrayOfNulls(0)
    private val KEY_CAMERA_POSITION = "camera pos"
    private val KEY_LOCATION = "location"
    private var locationPermission = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            lastLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        placesClients = Places.createClient(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        /* locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


         val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
         val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

         //gps Location Listener
         val gpsLocationListener: LocationListener = object : LocationListener {
             override fun onLocationChanged(location: Location) {
                  locationByGps = location
             }

         }
         val networkLocationListner: LocationListener = object : LocationListener {
             override fun onLocationChanged(location: Location) {
                  locationByNetwork = location
             }

         }
         if (hasGps) {
             if (ActivityCompat.checkSelfPermission(
                     this,
                     Manifest.permission.ACCESS_FINE_LOCATION
                 ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                     this,
                     Manifest.permission.ACCESS_COARSE_LOCATION
                 ) != PackageManager.PERMISSION_GRANTED
             ) {

                 return
             }
             locationManager.requestLocationUpdates(
                 LocationManager.GPS_PROVIDER,
                 5000,
                 0F,
                 gpsLocationListener
             )
         }
         if (hasNetwork) {
             locationManager.requestLocationUpdates(
                 LocationManager.NETWORK_PROVIDER,
                 5000,
                 0F,
                 networkLocationListner
             )
         }

         val lastLocationByGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
         lastLocationByGps.let {
          locationByGps = lastLocationByGps
         }
         val lastNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
         lastNetworkLocation.let {
          locationByNetwork = lastNetworkLocation
         }

         if (lastLocationByGps != null && lastNetworkLocation != null){
             if (lastLocationByGps.accuracy > lastNetworkLocation.accuracy){
                 currentLocation = locationByGps
             }
         }
 */


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        mMap?.let { map ->

            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastLocation)


        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.currentLocation) {
            showCurrentPlace()
        }
        return true
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        Log.e(TAG, "onMapReady: $sydney")
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        getLocationPermission()
    }

    //check Permission
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermission = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_CODE
            )
            checkCurosLocation()
        }

    }

    private fun checkCurosLocation() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermission = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                ACCESS_COARSE_LOCATION
            )

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermission = false
        when(requestCode){
            LOCATION_PERMISSION_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                     locationPermission = true
                    }
                } else {
                    Toast.makeText(applicationContext, "Permission Denied1", Toast.LENGTH_SHORT)
                        .show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null)
                            )
                        )
                    }
                }
            }
        }
    }


    //show current place
    private fun showCurrentPlace() {
         if (locationPermission) {
            val placeItem = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
            var request = FindCurrentPlaceRequest.newInstance(placeItem)
             var placeResult = placesClients.findCurrentPlace(request)


        }


    }

}