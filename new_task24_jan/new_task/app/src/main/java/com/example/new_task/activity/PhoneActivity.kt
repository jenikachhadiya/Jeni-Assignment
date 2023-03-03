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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.new_task.R
import com.example.new_task.databinding.ActivityPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class PhoneActivity : AppCompatActivity() {
    lateinit var binding: ActivityPhoneBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    companion object {
        private const val READ_NUMBER = 106
        private const val READ_PHONE_STATE = 107
        private const val SEND_SMS = 108
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Phone_number
        CheckPermissionPhoneNumber()
        checkSendSms()

        mAuth = FirebaseAuth.getInstance()

        //generate otp
        binding.rightIcon.setOnClickListener {

            var phNumber = binding.etPhoneNumber.text.toString()
            if (phNumber.isNotEmpty()) {
                if (phNumber.length == 10) {
                    phNumber = "+91$phNumber"
                    sendVerificationcode(phNumber)
                    overridePendingTransition(
                        R.anim.fade_enter_from_left,
                        R.anim.fade_enter_from_right
                    )
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "plz enter valid Phone Number ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        /* binding.rightIcon.setOnClickListener {
            var phNumber = binding.etPhoneNumber.text.toString()
            if (phNumber.isNotEmpty()) {
                if (phNumber.length == 10) {
                    phNumber = "+91$phNumber"
                    sendotp(phNumber)

                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "plz enter valid Phone Number ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }*/

        //back
        binding.backArrow.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            overridePendingTransition(R.anim.right_in, R.anim.right_out)
        }


        /* private fun sendotp(phNumber:String) {
        // callback methode
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(applicationContext, "$credential", Toast.LENGTH_SHORT).show()

            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                Log.e(TAG, "onCodeSent: $verificationId,$token",)
                //send token
                var intent = Intent(this@PhoneActivity,VerificationActivity::class.java)
                intent.putExtra("OTP", verificationId)
                intent.putExtra("Number","+91${binding.etPhoneNumber.text}")
                Log.e(TAG, "onCodeSent: $token" )
                startActivity(intent)
            }

        }
        var option:PhoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)

        // sendVerificationcode(binding.etPhoneNumber.text.toString().trim())
    }*/


        /*//code checkout correct or not
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            applicationContext,
                            "Authentication Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

    }*/

    }

    private fun CheckPermissionPhoneNumber() {
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
                            //  requestLocationPermission()
                        })
                        .create()
                        .show()
                } else {
                    requestCodeReadData()
                }
            }

        } else {
            checkPhoneState()
        }


    }

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
                READ_PHONE_STATE
            )
        }

    }

    private fun requestCodeReadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_PHONE_NUMBERS),
                READ_NUMBER
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
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
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
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
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            SEND_SMS ->{
                if (grantResults.isNotEmpty()  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.SEND_SMS
                    )==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext, "Permission Deny", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkSendSms() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.SEND_SMS),
                SEND_SMS
            )

        } else {
            Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
        }

    }


    //send code
    private fun sendVerificationcode(phNumber: String) {

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(applicationContext, "$credential", Toast.LENGTH_SHORT).show()

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                Log.e(TAG, "onCodeSent: $verificationId,$token")
                //send token
                var intent = Intent(applicationContext, VerificationActivity::class.java)
                intent.putExtra("VerificationId", verificationId)
                intent.putExtra("Token", token)
                intent.putExtra("Number", "+91${binding.etPhoneNumber.text}")
                Log.e(TAG, "onCodeSent: $phNumber")
                startActivity(intent)
            }

        }
        var option = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)

    }
}