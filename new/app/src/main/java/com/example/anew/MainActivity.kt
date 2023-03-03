package com.example.anew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.anew.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private  var itlist = mutableListOf<Rc>()

    lateinit var adop :RcAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //data adding
        dataadd()

        adop = RcAd(this,itlist)

         var manager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
        binding.rcView.layoutManager = manager
/*
        var manager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.rcView.layoutManager = manager*/

        binding.rcView.adapter = adop














    }

    private fun dataadd() {

        itlist.add(Rc(1,"Fashion",R.drawable.fashoin))
        itlist.add(Rc(2,"Appliances",R.drawable.appliances))
        itlist.add(Rc(3,"Beauty",R.drawable.beauty))
        itlist.add(Rc(4,"Book",R.drawable.book))
        itlist.add(Rc(5,"Deals",R.drawable.deals))
        itlist.add(Rc(6,"Electronic",R.drawable.electronic))
        itlist.add(Rc(7,"Fresh",R.drawable.fresh))
        itlist.add(Rc(8,"Furnichers",R.drawable.furnicher))
        itlist.add(Rc(9,"Home",R.drawable.home))
        itlist.add(Rc(10,"MiniTv",R.drawable.minitv))
        itlist.add(Rc(11,"Mobile",R.drawable.mobile))
        itlist.add(Rc(12,"Movies",R.drawable.movies))
        itlist.add(Rc(13,"Pharmarcy",R.drawable.pharmacy))
        itlist.add(Rc(14,"Travel",R.drawable.train1))
    }
}