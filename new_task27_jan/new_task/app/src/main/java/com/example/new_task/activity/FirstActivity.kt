package com.example.new_task.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.new_task.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)











    }
}