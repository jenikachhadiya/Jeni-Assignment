package com.example.anew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anew.databinding.ActivityWhatsupBinding

class Whatsup_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWhatsupBinding
    lateinit var Wpad : Wp_Ad
     var wplist  = mutableListOf<Wp>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatsupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //add data
        addData()
        //set data list to adoptor
        Wpad = Wp_Ad(applicationContext,wplist)
        //var manager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        binding.wpRcview.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        binding.wpRcview.adapter = Wpad



    }

    private fun addData() {

        wplist.add(Wp(1,"Fashion",R.drawable.fashoin,"How Are You??????",4.1f,"12:08Am"))
        wplist.add(Wp(2,"Beaty",R.drawable.beauty,"Can you..",2.1f,"11:55pm"))
        wplist.add(Wp(3,"Book",R.drawable.book,"My Name is .....",4.5f,"08:08Am"))
        wplist.add(Wp(4,"Deals",R.drawable.deals,"Can you pic up me in my Home",3.1f,"03:08Am"))
        wplist.add(Wp(5,"Electronic",R.drawable.electronic,"Bas...",1.12f,"05:13pm"))
        wplist.add(Wp(6,"Fresh",R.drawable.fresh,"I Know",3.5f,"08:08Am"))
        wplist.add(Wp(7,"Furnicher",R.drawable.furnicher,"I am fine",2.1f,"10:08Am"))
        wplist.add(Wp(8,"Home",R.drawable.home,"Can you are Online 5Min",2.5f,"11:09pm"))
        wplist.add(Wp(9,"MiniTv",R.drawable.minitv,"Mast haiiiiii",1.6f,"01:08Am"))
        wplist.add(Wp(10,"Movies",R.drawable.movies,"Jakas Movie hai....",3.1f,"7:02pm"))



//        wplist.add(Wp(1,"Fashion",R.drawable.fashoin))
//        wplist.add(Wp(2,"Beaty",R.drawable.beauty))
//        wplist.add(Wp(3,"Book",R.drawable.book))
//        wplist.add(Wp(4,"Deals",R.drawable.deals))

    }
}