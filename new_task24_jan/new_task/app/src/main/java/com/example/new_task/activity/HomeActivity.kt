package com.example.new_task.activity

import android.Manifest
import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.new_task.R
import com.example.new_task.databinding.ActivityHomeBinding
import com.example.new_task.fragment.*
import com.example.new_task.preference.PrefClass

class HomeActivity : AppCompatActivity() {
    companion object{
        private const val LOCATION_PERMISSION_CODE = 102
        private const val ACCESS_BACKGROUND_LOCATION = 103
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissionLocation()

        addFragment(HomeFragment(), getString(R.string.homefregment))

        var manger = PrefClass(this).getBagStatus()
        if (manger) {
            addFragment(BagFragment(), getString(R.string.bagfregment))
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            return@setOnItemSelectedListener when (it.itemId) {
                R.id.home -> {
                    addFragment(HomeFragment(), getString(R.string.homefregment))
                    true
                }
                R.id.profile -> {
                    addFragment(ProfileFragment(), getString(R.string.profilefregment))
                    true
                }
                R.id.setting -> {
                    addFragment(SettingFragments(), getString(R.string.settingfregment))
                    true
                }
                R.id.fav -> {
                    addFragment(FavoriteFragment(), getString(R.string.favfregment))
                    true
                }
                /*R.id.bag -> {
                    addFragment(BagFragment(), getString(R.string.bagfregment))
                    true
                }*/
                R.id.chat ->{
                    addFragment(ChatListFragment(),getString(R.string.chatFregment))
                    true
                }
                else -> {
                    false
                }
            }
        }


    }

    private fun addFragment(fragment: Fragment, tag: String) {
        val manger = supportFragmentManager
        val transaction = manger.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        transaction.replace(R.id.fram_layout, fragment, tag)
        transaction.commit()
        PrefClass(this).setSplashStatus(true)

    }
    private fun checkPermissionLocation() {
        //Location Permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This add Needs the Location Permission ,Please access to use Location functionality")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                        requestLocationPermission()
                    })
                    .create()
                    .show()
            } else {
                requestLocationPermission()
            }
        } else {
            checkBackgroundLocation()
        }


    }

    private fun checkBackgroundLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestBackgroundLocationPermission()
        }
    }
    private fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
               ACCESS_BACKGROUND_LOCATION
            )

        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
               LOCATION_PERMISSION_CODE
            )
        }

    }


    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            LOCATION_PERMISSION_CODE
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        checkBackgroundLocation()
                        // Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
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
                return
            }
            ACCESS_BACKGROUND_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

}