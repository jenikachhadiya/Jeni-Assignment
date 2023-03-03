package com.example.new_task.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import com.example.new_task.R
import com.example.new_task.databinding.ActivityVerificationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class VerificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityVerificationBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
     var otp:String? = null
    private lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken
      var number :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

         otp = intent.getStringExtra("VerificationId")
        number = intent.getStringExtra("Number")
        Log.e("Verify", "onCreate: $otp,$number")


        binding.otpView.setText(otp!!)
        //callback method
       /* callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //   var code = credential.smsCode
                Toast.makeText(applicationContext, "$credential", Toast.LENGTH_SHORT).show()
                signInWithPhoneAuthCredential(credential)

            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                Log.e(Constraints.TAG, "onCodeSent: $verificationId,$token",)
                otp = verificationId
                resendingToken = token

            }

        }

*/
      /*  binding.btnVerify.setOnClickListener {

            var otpcode = binding.otpView.toString()
            if (otpcode.isNotEmpty()){
                if (otpcode.length == 6){
                    val credentail :PhoneAuthCredential= PhoneAuthProvider.getCredential(
                        otp!!,otpcode)
                    signInWithPhoneAuthCredential(credentail)

                }else{
                    
                }
            }else{
                Toast.makeText(applicationContext, "plz enter otp", Toast.LENGTH_SHORT).show()
            }


        }*/
        /*//resend code
        binding.tvResentCode.setOnClickListener {
            resendOtp()
        }*/





    }
    //code checkout correct or not
  /*  private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   // val user = task.result?.user
                    startActivity(Intent(applicationContext,HomeActivity::class.java))
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

    }*/
    //resend otp
  /*  private fun resendOtp(){
        var option = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number.toString())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendingToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }*/


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)

    }
}
