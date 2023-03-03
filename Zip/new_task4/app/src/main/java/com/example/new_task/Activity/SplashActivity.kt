package com.example.new_task.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.new_task.Prefrencee.PrefClass
import com.example.new_task.databinding.ActivitySpBinding


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding = ActivitySpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manger = PrefClass(this)
        Thread{
            Thread.sleep(2000)

            if (!manger.getLoginStatus()) {
                startActivity(Intent(applicationContext, SignInActivity::class.java))
                finish()
            } else {
                startActivity(Intent(applicationContext, DataActivity::class.java))
                finish()
            }

        }.start()


    }
}