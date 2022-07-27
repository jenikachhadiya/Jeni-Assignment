package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FirstActivity : AppCompatActivity() {

    lateinit var btnContinue :Button
    lateinit var etName :EditText
    lateinit var etEmail :EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

         etName = findViewById(R.id.et_Name)
        etEmail=findViewById(R.id.et_Email)
        btnContinue = findViewById(R.id.btnContinue)

        btnContinue.setOnClickListener {
            var name = etName.text.toString().trim()
            var email = etEmail.text.toString().trim()


            var intent = Intent(applicationContext,SecondActivity::class.java)

            intent.putExtra("name",name)
            intent.putExtra("email",email)

               startActivity(intent)
        }



    }
}