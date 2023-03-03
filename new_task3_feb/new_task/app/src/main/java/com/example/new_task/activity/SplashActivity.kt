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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.new_task.databinding.ActivitySpBinding
import com.example.new_task.preference.PrefClass


class SplashActivity : AppCompatActivity() {
    companion object {
        private const val PUSH_NOTIFICATION_PERMISSION_CODE = 100


    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        checkPermission(Manifest.permission.POST_NOTIFICATIONS, PUSH_NOTIFICATION_PERMISSION_CODE)

        val title = intent.getStringExtra("Title")
        val message = intent.getStringExtra("Message")
        if (title != null) {
            var intent = Intent(applicationContext, ShopParchesActivity::class.java)
            intent.putExtra("Tit", title)
            intent.putExtra("Mes", message)
            startActivity(intent)
            finish()
            Log.e(TAG, "onNewIntent: $title")
            Log.e(TAG, "onNewIntent: $message")
        }
        val manger = PrefClass(this)
        if (!manger.getSplash()) {
            Thread {
                checkPermissionForReadExtertalStorage()
                Thread.sleep(2000)

                if (manger.getUserData() == null) {
                    startActivity(
                        Intent(
                            applicationContext,
                            SignInActivity::class.java
                        )
                    )


                    finish()
                } else {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                }

            }
                .start()
        } else {
          //  checkPermissionLocation()
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }


    }



    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PUSH_NOTIFICATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()) {
                    if (requestCode == PUSH_NOTIFICATION_PERMISSION_CODE) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            // permission granted
                            Toast.makeText(
                                this,
                                "NOTIFICATION Permission Granted",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            // permission denied
                            // Check wheather checked dont ask again
                            checkUserRequestedDontAskAgain()
                        }
                    }
                }
            }

        }


    }

    private fun checkPermissionForReadExtertalStorage(): Any {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED)
        } else if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                showAlertDialog()
                TODO("VERSION.SDK_INT < M")
            }
        ) {

        } else {
            false
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setMessage("This app needs you to allow this")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->

            }).setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->

            }).show()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PUSH_NOTIFICATION_PERMISSION_CODE
            )
        }
    }

    private fun checkUserRequestedDontAskAgain() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notification =
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
            if (!notification) {
                showAlertDialog()
               /* val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)*/
                Toast.makeText(applicationContext, "!notification", Toast.LENGTH_SHORT).show()
            }
        }
    }
    /*
 */


}





