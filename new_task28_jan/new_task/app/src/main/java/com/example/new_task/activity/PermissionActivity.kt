package com.example.new_task.activity

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.new_task.databinding.ActivityPermissionBinding

class PermissionActivity : AppCompatActivity() {

    companion object {
        private const val PUSH_NOTIFICATION_PERMISSION_CODE = 100
        private const val Contacts = 101
        private const val LOCATION_PERMISSION_CODE = 102
        private const val ACCESS_BACKGROUND_LOCATION = 103
        private const val Camera = 104
        private const val READ_NUMBER = 105
        private const val READ_PHONE_STATE = 106
        private const val SEND_SMS_CODE = 107
        private const val READ_STORAGE = 108
        private const val WRITE_STORAGE = 109
        private const val Calender = 110

    }

    val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.keys.contains(Manifest.permission.ACCESS_FINE_LOCATION) || it.keys.contains(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ){
                if (it[ACCESS_FINE_LOCATION]==true && it[ACCESS_COARSE_LOCATION] == true){
                    Toast.makeText(applicationContext, "Allow", Toast.LENGTH_SHORT).show()
                }else{
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this, Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        AlertDialog.Builder(this)
                            .setTitle("Location Permission Needed")
                            .setMessage("This add Needs the Location Permission ,Please access to use Location functionality")
                            .setPositiveButton("Ok") { _, _ ->
                                requestLocationPermission()
                            }
                            .create()
                            .show()
                    } else {
                        requestLocationPermission()
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPermission.setOnClickListener {
            showDialog()
        }


    }

    //List of Permissions
    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Permissions")
        val permission = arrayOf(
            "Location",
            "Camera",
            "Phone",
            "Read Phone Number",
            "Send SMS",
            "Read External Storage And Write External Storage",
            "Notification",
            "Contacts",
            "Calender"
        )
        builder.setItems(permission, DialogInterface.OnClickListener { _, item ->
            when (item) {
                0 -> {
                   // locationPermission()
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                    } else {
                        requestMultiplePermissions.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )


                        )
                    }

                }
                1 -> {
                  //  cameraPermission()
                }
                2 -> {
                  //  phonePermission()
                }
                3 -> {
                   // readPhoneNumber()
                }
                4 -> {
                   // sendAndReceiveSms()
                }
                5 -> {
                   // readExternalStorage()
                }
                6 -> {
                  //  notificationPermission()
                }
                7 -> {
                   // contactsPermission()
                }
                8 -> {
                  //  calenderPermission()
                }
            }
        })
        builder.create().show()


    }

    private fun calenderPermission() {
        if ((ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
                    ) && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR),
                Calender
            )
        } else {
            Toast.makeText(applicationContext, "Is Done", Toast.LENGTH_SHORT).show()
        }
    }

    //Contacts Permission
    private fun contactsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS), Contacts
            )
        } else {
            //if is granted
            Toast.makeText(applicationContext, "Is Done", Toast.LENGTH_SHORT).show()
        }

    }

    //Notification
    private fun notificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_DENIED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PUSH_NOTIFICATION_PERMISSION_CODE
                )
            }
        }
    }

    //Notification Request
    private fun checkUserRequestedDontAskAgain() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notification =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    showAlertDialog()
                    TODO("VERSION.SDK_INT < TIRAMISU")
                }
            if (!notification) {
                // showAlertDialog()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                Toast.makeText(applicationContext, "!notification", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //show dialog for need permission
    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setMessage("This app needs you to allow this")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->

            }).setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->

            }).show()
    }

    //read and write Permission
    private fun readExternalStorage() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_MEDIA_IMAGES
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("READ_EXTERNAL_STORAGE Permission Needed")
                    .setMessage("This add Needs the Location Permission ,Please access to use Location functionality")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                        requestReadPermission()
                    })
                    .create()
                    .show()
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(applicationContext, "Setting in Allowed", Toast.LENGTH_SHORT)
                        .show()
                }
                requestReadStoragePermission()
            }
        }
    }

    //Send and View Sms
    private fun sendAndReceiveSms() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
            ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
                //user come deny but this is only one time execute bcoz second time this is return false
                AlertDialog.Builder(this)
                    .setTitle("SMS Permission Needed")
                    .setMessage("This add Needs the Send Sms Permission ,Please access to use to Send Otp")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                        locationSendSmsPermission()
                        //requestRecPermission()
                    })
                    .create()
                    .show()
            } else {
                locationSendSmsPermission()
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(applicationContext, "All ready Allow", Toast.LENGTH_SHORT).show()
            } else {
                locationSendSmsPermission()
            }
        }


    }

    //Request Send Sms
    private fun locationSendSmsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.SEND_SMS,
            ),
            SEND_SMS_CODE
        )
    }

    //Location
    private fun locationPermission() {
        //show permission
        checkPermissionLocation()
    }

    // Location permission
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

    //Access_location code
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            LOCATION_PERMISSION_CODE
        )
    }

    //Background Location
    private fun checkBackgroundLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestBackgroundLocationPermission()
        }
    }

    //check location Version Code
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

    //Camera code
    private fun cameraPermission() {
        checkPermissionCamera()

    }

    //Camera Request
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
            ),
            Camera
        )
    }

    //camera Permission
    private fun checkPermissionCamera() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Camara Permission Needed")
                    .setMessage("This add Needs the Camera Permission ,Please access to use Camera functionality")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                        requestCameraPermission()
                    })
                    .create()
                    .show()
            } else {
                requestCameraPermission()
            }
        }
    }

    //Phone Number
    private fun readPhoneNumber() {
        checkPhoneState()
    }

    //phone Permission
    private fun phonePermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_NUMBERS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.READ_PHONE_STATE
                    )
                ) {
                    AlertDialog.Builder(this)
                        .setTitle(" Phone Permission  Needed")
                        .setMessage("This add Needs the Location Permission ,Please access to use Location functionality")
                        .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->

                        })
                        .create()
                        .show()
                } else {
                    requestCodeReadData()
                }
            }
        }
    }

    //Read_Phone
    private fun requestCodeReadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_NUMBERS),
                READ_NUMBER
            )
        }
    }

    //Read_Phone_State
    private fun checkPhoneState() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestStatePermission()
        }
    }

    private fun requestStatePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_STATE),
                READ_PHONE_STATE
            )
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_STATE),
                READ_NUMBER
            )
        }

    }

    private fun requestReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_STATE),
                READ_PHONE_STATE
            )
            requestStatePermission()
        }

    }

    //read storage
    private fun requestReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                ),
                READ_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
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
                    }
                } else {
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
                    return
                }
            }
            ACCESS_BACKGROUND_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            applicationContext,
                            "Permission granted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            applicationContext,
                            "Allow",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }
            Camera -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            applicationContext,
                            "Permission granted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext, "Allow Camera", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.CAMERA
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
            READ_NUMBER -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_PHONE_NUMBERS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        requestCodeReadData()
                        // Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Permission Denied PN", Toast.LENGTH_SHORT)
                        .show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_PHONE_NUMBERS
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
            READ_PHONE_STATE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_PHONE_STATE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
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
                    Toast.makeText(applicationContext, "Permission Denied RPS", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            SEND_SMS_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.SEND_SMS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext, "Allow", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.SEND_SMS
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null)
                            )
                        )
                    }
                    Toast.makeText(applicationContext, "Permission Deny", Toast.LENGTH_SHORT).show()
                }
            }
            READ_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Permission Denied",
                            Toast.LENGTH_SHORT
                        ).show()
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        ) {
                            readExternalStorage()
                        }
                    }
                } else {
                    startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", this.packageName, null)
                        )
                    )
                }

            }
            WRITE_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            PUSH_NOTIFICATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission granted
                        Toast.makeText(
                            this,
                            "NOTIFICATION Permission Granted",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        checkUserRequestedDontAskAgain()
                    }

                }


            }
            Contacts -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_CONTACTS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext, "Allow Contacts", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_CONTACTS
                        )
                    ) {
                        AlertDialog.Builder(this)
                            .setTitle("Contacts Permission Needed")
                            .setMessage("This add Needs the Contacts Permission ,Please access to use Contacts functionality")
                            .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                                startActivity(
                                    Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", this.packageName, null)
                                    )
                                )
                            })
                            .create()
                            .show()

                    } else {
                        Toast.makeText(applicationContext, "Deny Contacts", Toast.LENGTH_SHORT)
                            .show()
                    }
                    return
                }
            }
            Calender -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_CALENDAR
                        ) == PackageManager.PERMISSION_GRANTED
                                ) && ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_CALENDAR
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext, "Allow Calender", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    if ((!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_CALENDAR
                        )
                                ) && (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_CALENDAR
                        ))
                    ) {
                        AlertDialog.Builder(this)
                            .setTitle("Calender Permission Needed")
                            .setMessage("This add Needs the Calender Permission ,Please access to use Contacts functionality")
                            .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                                startActivity(
                                    Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", this.packageName, null)
                                    )
                                )
                            })
                            .create()
                            .show()

                    } else {
                        Toast.makeText(applicationContext, "Deny Calender", Toast.LENGTH_SHORT)
                            .show()
                    }
                    return
                }
            }
        }
    }
}
