package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.databinding.ActivityFregmentBinding

class Fregment : AppCompatActivity() {

    private lateinit var binding: ActivityFregmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // changins in using view binding..
        binding =ActivityFregmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manger : FragmentManager= supportFragmentManager
        val tra :FragmentTransaction = manger.beginTransaction()

        val fregment = Firstfregment()

        tra.add(R.id.Fragment_layout,fregment).commit()



    }
}


