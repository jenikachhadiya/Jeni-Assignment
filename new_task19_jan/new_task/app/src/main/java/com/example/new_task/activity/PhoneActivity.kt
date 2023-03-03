package com.example.new_task.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.new_task.R
import com.example.new_task.databinding.ActivityPhoneBinding


class PhoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}