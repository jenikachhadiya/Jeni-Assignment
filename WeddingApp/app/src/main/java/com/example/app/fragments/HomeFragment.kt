package com.example.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.activitys.SettingActivity
import com.example.app.activitys.TaskActivity
import com.example.app.adapter.ClothingAdapter
import com.example.app.adapter.PhotographyAdapter
import com.example.app.adapter.WeddingAdapter
import com.example.app.model.Cloth
import com.example.app.model.Wedding
import com.example.weddingapp.R
import com.example.weddingapp.databinding.FragmentHomeBinding
import com.example.weddingapp.databinding.FragmentTaskBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    var weddingList = mutableListOf<Wedding>()
    lateinit var weddingHallAdapter: WeddingAdapter


    var photographyList = mutableListOf<Wedding>()
    lateinit var photographyAdapter: PhotographyAdapter

    var clothingList = mutableListOf<Cloth>()
    lateinit var clothingAdapter: ClothingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        preparaData()


        weddingHallAdapter = WeddingAdapter(requireContext(), weddingList)
        binding.recyclerviewItem.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewItem.adapter = weddingHallAdapter

        photographyAdapter = PhotographyAdapter(requireContext(), photographyList)
        binding.photographerRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.photographerRecyclerview.adapter = photographyAdapter

        clothingAdapter = ClothingAdapter(requireContext(), clothingList)
        binding.clothingRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.clothingRecyclerview.adapter = clothingAdapter

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivCart.setOnClickListener {
            startActivity(Intent(requireActivity(), TaskActivity::class.java))
        }
    }

    private fun preparaData() {


        weddingList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        weddingList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        weddingList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        weddingList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        weddingList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        weddingList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )

        photographyList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        photographyList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        photographyList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        photographyList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        photographyList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )
        photographyList.add(
            Wedding(
                1,
                R.drawable.iv_weddinglist1,
                "Koregaon Hall",
                "Paris",
                800.2,
                R.drawable.ic_star,
                2.5
            )
        )


        clothingList.add(Cloth(1, R.drawable.iv_weddinglist1, "Koregaon Hall", 800.2))
        clothingList.add(Cloth(1, R.drawable.iv_weddinglist1, "Koregaon Hall", 800.2))
        clothingList.add(Cloth(1, R.drawable.iv_weddinglist1, "Koregaon Hall", 800.2))
        clothingList.add(Cloth(1, R.drawable.iv_weddinglist1, "Koregaon Hall", 800.2))
        clothingList.add(Cloth(1, R.drawable.iv_weddinglist1, "Koregaon Hall", 800.2))
        clothingList.add(Cloth(1, R.drawable.iv_weddinglist1, "Koregaon Hall", 800.2))

    }
}