package com.example.new_task.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Transformations.map
import com.example.new_task.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.net.PlacesClient
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val LOCATION_PERMISSION_CODE = 19
        private const val DEFAULT_ZOOM = 15f
        private const val ENTRIES = 5

    }


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var currentLocation: Location
    private var cameraPosition: CameraPosition? = null
    private lateinit var marker: ImageView


    //Api
    private lateinit var placesClients: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    //location rec by fusedLocationProvider
    private var lastLocation: Location? = null
    private var placeName: Array<String?> = arrayOfNulls(0)
    private var placeAddress: Array<String?> = arrayOfNulls(0)
    private var latLng: Array<LatLng?> = arrayOfNulls(0)
    private var locationPermission = false
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val mGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        Log.e(TAG, "onCreate: $mGps")
        if (mGps) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fetchLocation()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Turn On Location Mode")
                .setMessage("This add Needs the  Current Location  ,Please access to use Location functionality")
                .setPositiveButton("Ok") { _, _ ->
                    startActivity(
                        Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        )
                    )

                }
                .create()
                .show()
            if (!mGps) {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fetchLocation()
            }


        }
        /*
         Places.initialize(applicationContext,BuildConfig.BUILD_TYPE)
         placesClients = Places.createClient(this)
       */
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /* val mapFragment = supportFragmentManager
             .findFragmentById(R.id.map) as SupportMapFragment
         mapFragment.getMapAsync(this)*/
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
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation = location
                        Toast.makeText(
                            applicationContext, currentLocation.latitude.toString() + "" +
                                    (currentLocation.longitude), Toast.LENGTH_SHORT
                        ).show()
                       marker =  binding.tvMap
                        val supportMapFragment =
                            (supportFragmentManager.findFragmentById(R.id.map) as
                                    SupportMapFragment?)!!
                        supportMapFragment.getMapAsync(this)
                    }

                }
        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        /* mMap?.let { map ->
             outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
             outState.putParcelable(KEY_LOCATION, lastLocation)
         }*/
        super.onSaveInstanceState(outState)
    }

    /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
          menuInflater.inflate(R.menu.location, menu)
          return true
      }

      override fun onOptionsItemSelected(item: MenuItem): Boolean {
          if (item.itemId == R.id.currentLocation) {
              showCurrentPlace()
          }
          return true
      }
  */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var addressList: List<Address>? = null
        val geocoder: Geocoder = Geocoder(this@MapsActivity)

        binding.searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val location = binding.searchBar.query.toString()


                mMap.uiSettings.isCompassEnabled = true;
                mMap.uiSettings.setAllGesturesEnabled(true);
                mMap.uiSettings.isZoomControlsEnabled = true;

                try {
                    addressList = geocoder.getFromLocationName(location, ENTRIES)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val address: Address? = addressList?.get(0)
                val latLng = LatLng(address!!.latitude, address.longitude)
               // var latlangpostion = LatLng(address!!.latitude, address.longitude)
                mMap.addMarker(
                    MarkerOptions().position(latLng).title(location)
                        //.icon(BitmapFromVector(applicationContext, R.drawable.ic_sharp_flag_24))
                )
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM))
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
       // val marker = (MarkerOptions().position(latLng).title("i am here")
           // .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_sharp_flag_24)))
            //.icon(BitmapFromVector(applicationContext, R.drawable.ic_sharp_flag_24)).draggable(true))
        Log.e(TAG, "onMapReady: $latLng")
       // mMap . animateCamera (CameraUpdateFactory.newLatLng(latLng))
        Log . e(TAG, "onMapReady: $currentLocation")
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM))
       // mMap.addMarker(marker)
        googleMap.uiSettings.isZoomGesturesEnabled = true;
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.id.normal))
        )





    }


    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)
        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)
        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
    /* private fun bitmapFromVector(context: Context, Vector: Int) {
       var  vectorDrawable = ContextCompat.getDrawable(context,Vector)
         vectorDrawable?.setBounds(0, 0,vectorDrawable.intrinsicWidth,vectorDrawable.intrinsicHeight)
         var bitmap :Bitmap = Bitmap.createBitmap(vectorDrawable?.intrinsicWidth,vectorDrawable?.intrinsicHeight)
     }
     */
/*
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

        }

    }
    @SuppressLint("MissingPermission")
    private fun checkCurosLocation() {
        if(mMap == null){
            return
        }
        try {
            if (locationPermission){
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true

            }else{
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                lastLocation = null
                getLocationPermission()
            }
        }catch (e:SecurityException){
            Log.e(TAG, "checkCurosLocation: $e", )
        }
      *//*
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

        }*//*

    }
    @SuppressLint("MissingPermission")
    private fun getDeviesLocation(){
        try {
            if (locationPermission) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastLocation = task.result
                        if (lastLocation != null) {
                            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastLocation!!.latitude,
                                    lastLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        mMap?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        mMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }*/

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
/*
    private fun turOnGps() {
     var provider = Settings.Secure.getString(contentResolver,Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
        if (!provider.contains("gps")){
            var intent = Intent()
            intent.setClassName("com.example.new_task","com.example.new_task.activity")
            intent.addCategory(Intent.CATEGORY_ALTERNATIVE)
            intent.setData(Uri.parse("3"))
            sendBroadcast(intent)
        }
    }*/

/*
    //show current place
    @SuppressLint("MissingPermission")
    private fun showCurrentPlace() {
         if (locationPermission) {
            val placeItem = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val request = FindCurrentPlaceRequest.newInstance(placeItem)
             var placeResult = placesClients.findCurrentPlace(request)
             placeResult.addOnCompleteListener { task ->
                 if (task.isSuccessful && task.result != null){
                     val likeplace = task.result

                     val count = if(likeplace != null && likeplace.placeLikelihoods.size < ENTRIES)
                     {
                         likeplace.placeLikelihoods.size
                     }else{
                         ENTRIES
                     }

          *//*   var i = 0
             likePlaceNames = arrayOfNulls(count)
             likePlaceAddresses = arrayOfNulls(count)
             likelyPlaceLatLngs = arrayOfNulls(count)*//*







                 }
             }

        }


    }*/

}

