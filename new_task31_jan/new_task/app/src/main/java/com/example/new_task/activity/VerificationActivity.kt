package com.example.new_task.activity


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.new_task.R
import com.example.new_task.databinding.ActivityVerificationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import java.util.concurrent.TimeUnit

class VerificationActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var resendingToken: ForceResendingToken
    var mVerificationId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        val otp = intent.getStringExtra("VerificationId")
        val number = intent.getStringExtra("Number")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            resendingToken = intent.getParcelableExtra(
                "Token",
                PhoneAuthProvider.ForceResendingToken::class.java
            )!!
        }
        // resendingToken = PhoneAuthProvider.ForceResendingToken
        Log.e(TAG, "onCreate: $number")

        mVerificationId = otp!!

        binding.btnVerify.setOnClickListener {

            val otpcode = binding.otpView.getStringFromFields()
            if (otpcode.isNotEmpty()) {
                if (otpcode.length == 6) {

                    val credential: PhoneAuthCredential =
                        PhoneAuthProvider.getCredential(mVerificationId, otpcode)

                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(applicationContext, "plz Enter 6 Number", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "plz enter otp", Toast.LENGTH_SHORT).show()
            }


        }
        //resend Otp
        binding.tvResentCode.setOnClickListener {
            if (number != null) {
                resendOtp(number, resendingToken)
            }
        }
    }

    //code checkout correct or not
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                   // Log.e(TAG, "signInWithPhoneAuthCredential: $user")
                   // Toast.makeText(applicationContext, "$user", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
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
    }


    //resend otp
    private fun resendOtp(phNumber: String, resendingToken: ForceResendingToken) {

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Toast.makeText(applicationContext, "$credential", Toast.LENGTH_SHORT).show()
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                Log.e(TAG, "onCodeSent: $verificationId,$token")
                mVerificationId = verificationId
                this@VerificationActivity.resendingToken = token

            }

        }
        val option = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phNumber)
            .setTimeout(10L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendingToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)

    }
}
