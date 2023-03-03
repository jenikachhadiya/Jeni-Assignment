package com.example.new_task

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.new_task.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val topic = binding.tvTask.text.toString()
        firebaseAnalytics = Firebase.analytics

        //subscribe check
        binding.subscribeId.setOnClickListener {
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "subscribe", Toast.LENGTH_SHORT).show()
                }
        }
        binding.unsubscribeId.setOnClickListener {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "Unsubscribe", Toast.LENGTH_SHORT).show()
                }
        }


    }

}