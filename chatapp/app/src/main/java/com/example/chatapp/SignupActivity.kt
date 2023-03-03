package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity(){

    private lateinit var mAuth:FirebaseAuth
    private lateinit var db:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        binding.btnSignup.setOnClickListener {
            var name = binding.etName.text.toString().trim()
            var email = binding.etEmail.text.toString().trim()
            var pass = binding.etPass.text.toString().trim()

            sighup(name,email,pass)


        }




    }
    private fun sighup(name:String,email:String,pass:String){
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    adddatatoDatabase(name,email,mAuth.uid!!)
                   startActivity(Intent(applicationContext,ChatListActivity::class.java))

                } else {
                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun adddatatoDatabase(name: String, email: String, uid: String) {
        db = FirebaseDatabase.getInstance().getReference()
        db.child("user").child(uid).setValue(Chat(uid,name,email))

    }
}
