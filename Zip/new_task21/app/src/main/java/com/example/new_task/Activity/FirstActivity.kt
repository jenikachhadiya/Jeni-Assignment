package com.example.new_task.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.new_task.Adaptor.AutoSliderAdaptor
import com.example.new_task.ListUnity.ImgList
import com.example.new_task.R
import com.example.new_task.databinding.ActivityFirstBinding
import com.example.new_task.entity.ImageSlider

class FirstActivity : AppCompatActivity() {
    lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)











    }
}