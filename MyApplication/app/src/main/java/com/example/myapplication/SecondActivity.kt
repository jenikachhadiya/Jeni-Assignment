package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
      val tvname :TextView
   get() = findViewById(R.id.tv_Name)

    val tvemail : TextView
    get() = findViewById(R.id.tv_Email)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var name = intent.getStringExtra("name")
        var email =intent.getStringExtra("email")

        tvname.text = "Name = $name"
        tvemail.text ="Email =$email"

    }
}