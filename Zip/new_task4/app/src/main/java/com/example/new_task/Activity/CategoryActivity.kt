package com.example.new_task.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.databinding.ActivityCategoryBinding
import com.example.new_task.entity.Category



class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*
//Category click Event Data Get
        var getData =  intent.getParcelableExtra<Category>("category")

        if (getData!=null){
          binding.tvName.text  = getData.Title
            Glide
                .with(applicationContext)
                .load(getData.Img)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_sync_24)
                .into(binding.image)

        }
*/




    }
}