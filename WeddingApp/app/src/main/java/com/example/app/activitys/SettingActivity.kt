package com.example.app.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app.shredpref.PrefManagr
import com.example.weddingapp.databinding.ActivitySettingBinding


class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnDelete.setOnClickListener {

            var prefManagr = PrefManagr(this)
            prefManagr.setLoginStatus(false)

            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()
        }

        binding.btnBack.setOnClickListener {

            onBackPressed()

        }
    }
}