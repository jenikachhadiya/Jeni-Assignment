package com.example.app.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app.fragments.TaskFragment
import com.example.weddingapp.R
import com.example.weddingapp.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    private lateinit var binding:ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var fragment = TaskFragment()
        var manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        transaction.add(R.id.fragment_container, fragment)
        transaction.commit()
    }
}