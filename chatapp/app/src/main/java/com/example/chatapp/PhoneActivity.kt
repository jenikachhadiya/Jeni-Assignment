package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.chatapp.databinding.ActivityPhoneBinding

import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class PhoneActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhoneBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        //generate otp
        binding.rightIcon.setOnClickListener {
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

        }
        //back
       /* binding.backArrow.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            overridePendingTransition(R.anim.right_in, R.anim.right_out)
        }*/


    }

    private fun sendotp(phNumber:String) {
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
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()


    }
}