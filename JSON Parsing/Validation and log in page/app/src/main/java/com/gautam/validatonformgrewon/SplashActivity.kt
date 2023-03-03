package com.gautam.validatonformgrewon

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gautam.validatonformgrewon.databinding.ActivitySplashBinding
import com.gautam.validatonformgrewon.shareprefrence.PrefManager

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent?.getStringExtra("title")

        if (name != null) {
            val description = intent.getStringExtra("messagebody")

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("title", name)
            intent.putExtra("messagebody", description)
            startActivity(intent)
            finish()

        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val prefManagr = PrefManager(this)
                if (  prefManagr.getApi() != null) {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()

                } else {
                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }, 300)
        }
    }
}
