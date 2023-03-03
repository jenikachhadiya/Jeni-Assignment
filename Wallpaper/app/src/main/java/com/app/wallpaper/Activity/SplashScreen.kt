package com.app.wallpaper.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.wallpaper.R
import com.app.wallpaper.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread(Runnable {
            try{
                Thread.sleep(500)
                startActivity(Intent(applicationContext,Image_Details::class.java))
            }catch (e:Exception){

            }

        }).start()

        Toast.makeText(applicationContext, "navigration", Toast.LENGTH_SHORT).show()

    }
}