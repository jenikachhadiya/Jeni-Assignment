package com.app.wallpaper.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.wallpaper.R
import com.app.wallpaper.databinding.ActivityImgBinding

class ImgActivity : AppCompatActivity() {
    lateinit var binding: ActivityImgBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImgBinding.inflate(layoutInflater)

        //Back Arrow Clicke Event
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        setContentView(binding.root)
    }
}