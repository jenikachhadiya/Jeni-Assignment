package com.example.new_task.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.new_task.R
import com.example.new_task.databinding.ActivityGoogleMapBinding

class GoogleMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGoogleMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}