package com.app.wallpaper.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.wallpaper.Activity.Image_Details
import com.app.wallpaper.Adaptor.categories_adaptor
import com.app.wallpaper.Interface.OnCategoriesListner
import com.app.wallpaper.Modal.categories
import com.app.wallpaper.R
import com.app.wallpaper.databinding.CategoriesLayoutBinding
import com.app.wallpaper.databinding.FragmentCategoriesBinding
import com.app.wallpaper.databinding.FragmentImgBinding


class categories : Fragment() {
    lateinit var binding: FragmentCategoriesBinding
    lateinit var Cate_adaptor: categories_adaptor
    var Catelist = mutableListOf<categories>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(layoutInflater)

        Catelist = mutableListOf()

        Cate_adaptor = categories_adaptor(requireActivity(), Catelist)
        var manger = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recView.layoutManager = manger
        binding.recView.adapter = Cate_adaptor

        DataInsert()
        Cate_adaptor.catelistner(object : OnCategoriesListner {

            override fun categorieslistner(categories: categories, position: Int) {

                Toast.makeText(
                    requireActivity(),
                    "${categories.Tital},${position}",
                    Toast.LENGTH_SHORT
                ).show()
                //if (position == 0) {
                    Toast.makeText(requireActivity(), "navigration", Toast.LENGTH_SHORT).show()
                    var intent = Intent(requireActivity(),Image_Details::class.java)
                    intent.putExtra("tag","${categories.Tital}")
                     startActivity(intent)
               // }


            }

        })
        return binding.root





    }

    private fun DataInsert() {

        Catelist.add(categories(1, R.drawable.img, "Abstract"))
        Catelist.add(categories(2, R.drawable.animal, "Animals"))
        Catelist.add(categories(3, R.drawable.artistic, "Artistic"))
        Catelist.add(categories(4, R.drawable.astronomy, "Astronomy"))
        Catelist.add(categories(5, R.drawable.autum, "Autumn"))
        Catelist.add(categories(6, R.drawable.nat, "Nature"))
        Catelist.add(categories(7, R.drawable.bird, "Birds"))

    }



}