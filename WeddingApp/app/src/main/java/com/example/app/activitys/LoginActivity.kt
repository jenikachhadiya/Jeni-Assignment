package com.example.app.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.MainActivity
import com.example.app.database.Appdatabase
import com.example.app.shredpref.PrefManagr
import com.example.weddingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var db: Appdatabase


    private val REGEX = ("^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Appdatabase.getDatabase(this)



        binding.btnSignup.setOnClickListener {

            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {


            var email = binding.etEmail.text.toString().trim()
            var password = binding.etPassword.text.toString().trim()


            var prefManagr = PrefManagr(this)
            prefManagr.setLoginStatus(true)


            if (!isvalidEmail(email)) {
                binding.tvEmail.error = "Enter your name"
            } else if (!isvalidpassword(password)) {
                binding.tvPassword.error = "Enter your email"
            } else {
//                val user = User(
//                    email = email,
//                    password = password
//                )
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(applicationContext, "All fields are validated", Toast.LENGTH_SHORT)
                    .show()

//                db.userDao.insertRecord(user)
//                Toast.makeText(applicationContext, "data save", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun isvalidpassword(contact: String): Boolean {
        return contact.length == 10
    }

    private fun isvalidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}