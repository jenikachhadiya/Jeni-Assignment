package com.example.new_task.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.new_task.databinding.ActivityPermissionBinding

class PermissionActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPermissionBinding

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
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            if (permission.keys.contains(Manifest.permission.ACCESS_FINE_LOCATION) || permission.keys.contains(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                if (permission[Manifest.permission.ACCESS_FINE_LOCATION] == true && permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                    Toast.makeText(
                        this,
                        "LOCATION ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "denai",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.CAMERA)) {

                if (permission[Manifest.permission.CAMERA] == true) {
                    Toast.makeText(
                        this,
                        "camera",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "denai",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else if (permission.keys.contains(Manifest.permission.READ_CALL_LOG) || permission.keys.contains(
                    Manifest.permission.WRITE_CALL_LOG
                )
            ) {

                if (permission[Manifest.permission.READ_CALL_LOG] == true && permission[Manifest.permission.READ_CALL_LOG] == true) {

                    Toast.makeText(
                        this,
                        "CALL_LOG permission granted by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "CALL_LOG permission denied by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.READ_EXTERNAL_STORAGE) || permission.keys.contains(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                if (permission[Manifest.permission.READ_EXTERNAL_STORAGE] == true && permission[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) {

                    Toast.makeText(
                        this,
                        "STORAGE permission granted by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "STORAGE permission denied by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else if (permission.keys.contains(Manifest.permission.READ_CONTACTS) || permission.keys.contains(
                    Manifest.permission.WRITE_CONTACTS
                )
            ) {

                if (permission[Manifest.permission.READ_CONTACTS] == true && permission[Manifest.permission.WRITE_CONTACTS] == true) {

                    Toast.makeText(
                        this,
                        "CONTACTS permission granted by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "CONTACTS permission denied by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.SEND_SMS) || permission.keys.contains(
                    Manifest.permission.RECEIVE_SMS
                )
            ) {

                if (permission[Manifest.permission.SEND_SMS] == true && permission[Manifest.permission.RECEIVE_SMS] == true) {

                    Toast.makeText(
                        this,
                        "SMS permission granted by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "SMS permission denied by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.READ_CALENDAR) || permission.keys.contains(
                    Manifest.permission.WRITE_CALENDAR
                )
            ) {

                if (permission[Manifest.permission.READ_CALENDAR] == true && permission[Manifest.permission.WRITE_CALENDAR] == true) {

                    Toast.makeText(
                        this,
                        "CALENDAR permission granted by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "CALENDAR permission denied by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.RECORD_AUDIO)) {

                if (permission[Manifest.permission.RECORD_AUDIO] == true) {

                    Toast.makeText(
                        this,
                        "RECORD_AUDIO permission granted by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "RECORD_AUDIO permission denied by the user",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    var requestPermissionAudio =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == true) {
                Log.e("TAG", "audio" + it)
                Toast.makeText(
                    this,
                    "audio permission granted by the user",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "permission denied by the user",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
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
            "read call log",
            "Storage",
            "Contacts",
            "Send SMS",
            "Calender",
            "Audio",
            "Gallery"
        )
        builder.setItems(permission, DialogInterface.OnClickListener { _, item ->
            when (item) {
                0 -> {
                    locationPermission()
                }
                1 -> {
                    cameraPermission()
                }
                2 -> {
                    readcallLog()
                }
                3 -> {
                    storagePermission()
                }
                4 -> {
                    contactsPermission()
                }
                5 -> {
                    sendAndReceiveSms()
                }
                6 -> {
                    calenderPermission()
                }
                7 -> {

                    audioPermission()
                }
                8 -> {
                    // notificationPermission()
                    galleryPermission()
                }
            }
        })
        builder.create().show()


    }

    private fun galleryPermission() {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun audioPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("TAG", "RECORD_AUDIO: AUDIO ")
        }
        else if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO))
        {
            DilogeShow(Manifest.permission.RECORD_AUDIO)
        } else {
            requestPermissionAudio.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun storagePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        }else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            DilogeShow(Manifest.permission.READ_EXTERNAL_STORAGE + "  " + Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }   else {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun calenderPermission() {
        /* if ((ActivityCompat.checkSelfPermission(
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
         }*/
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR) && shouldShowRequestPermissionRationale(
                Manifest.permission.WRITE_CALENDAR
            )
        ) {
            DilogeShow(Manifest.permission.READ_CALENDAR + "  " + Manifest.permission.WRITE_CALENDAR)
        } else {

            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS
                )
            )
        }
    }

    //Contacts Permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun contactsPermission() {
        /*  if (ContextCompat.checkSelfPermission(
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
          }*/

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        }
        else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) && shouldShowRequestPermissionRationale(
                Manifest.permission.WRITE_CONTACTS
            )
        ) {
            DilogeShow(Manifest.permission.READ_CONTACTS + "  " + Manifest.permission.WRITE_CONTACTS)
        } else {

            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS
                )
            )
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
        /* if (ActivityCompat.checkSelfPermission(
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
         }*/
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        }else if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS) && shouldShowRequestPermissionRationale(
                Manifest.permission.RECEIVE_SMS
            )
        ) {
            DilogeShow(Manifest.permission.SEND_SMS + "  " + Manifest.permission.RECEIVE_SMS)
        }  else {

            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS
                )
            )
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
    @RequiresApi(Build.VERSION_CODES.M)
    private fun locationPermission() {
        //show permission
        //checkPermissionLocation()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT).show()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) && shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            DilogeShow(Manifest.permission.ACCESS_FINE_LOCATION + "  " + Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

        }
    }

    /* // Location permission
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

     }*/

    //Camera code
    @RequiresApi(Build.VERSION_CODES.M)
    private fun cameraPermission() {
        //checkPermissionCamera()
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // permission already granted
            Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT).show()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            DilogeShow(Manifest.permission.CAMERA)
        } else {
            requestMultiplePermissions.launch(arrayOf(Manifest.permission.CAMERA))
            Toast.makeText(applicationContext, "Permisssion Denid", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun DilogeShow(permission: String) {
        AlertDialog.Builder(this)
            .setTitle("Permissition Requed..")
            .setMessage("This Application Need  $permission  so Better Using App Features and Functionality Pleases Allowed  $permission  Permission ")
            .setPositiveButton("Allowed", DialogInterface.OnClickListener { dialogInterface, i ->
                //permisstion granted navigration to setting page
                var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                var uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                Toast.makeText(applicationContext, "permission granded", Toast.LENGTH_SHORT).show()
            }).setNegativeButton("Cancle", DialogInterface.OnClickListener { dialogInterface, i ->
                //permisstion Denied
                Toast.makeText(applicationContext, "permission Denied", Toast.LENGTH_SHORT).show()
            }).show()
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

    /* //camera Permission
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
 */
    //Phone Number
    @RequiresApi(Build.VERSION_CODES.M)
    private fun readcallLog() {
        // checkPhoneState()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALL_LOG
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CALL_LOG
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        }else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG) && shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALL_LOG)) {
            DilogeShow(Manifest.permission.READ_CALL_LOG+"  "+Manifest.permission.WRITE_CALL_LOG)
        }else {

            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.WRITE_CALL_LOG
                )
            )
        }

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
/*    private fun checkPhoneState() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestStatePermission()
        }
    }*/

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

    /* override fun onRequestPermissionsResult(
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
                        // checkBackgroundLocation()
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
     }*/
}
