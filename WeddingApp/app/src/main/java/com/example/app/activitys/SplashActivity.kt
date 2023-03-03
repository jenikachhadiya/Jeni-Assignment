package com.example.app.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app.MainActivity
import com.example.app.shredpref.PrefManagr
import com.example.weddingapp.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var prefManagr = PrefManagr(this)

        Thread(
            Runnable {
                if (!prefManagr.getLoginStatus()) {
                    Thread.sleep(3000)
                    startActivity(Intent(this, LoginActivity::class.java))
                    startActivity(intent)
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    startActivity(intent)
                }
                finishAffinity()
            }
        ).start()

   }
}