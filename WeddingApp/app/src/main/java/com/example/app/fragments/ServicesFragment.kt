package com.example.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.app.adapter.ServicesAdapter

import com.example.app.model.Services
import com.example.weddingapp.R
import com.example.weddingapp.databinding.FragmentServicesBinding


class ServicesFragment : Fragment() {


    private lateinit var binding: FragmentServicesBinding

    var serviceList = mutableListOf<Services>()
    lateinit var servicesAadapter: ServicesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentServicesBinding.inflate(inflater, container, false)


        preparaData()

        servicesAadapter = ServicesAdapter(requireContext(), serviceList)
        binding.servicesRecyclerviewItem.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.servicesRecyclerviewItem.adapter = servicesAadapter



        return binding.root
    }

    private fun preparaData() {

        serviceList.clear()
        serviceList.add(
            Services(
                1,
                R.drawable.ic_wedding_icon,
                R.drawable.line_icon,
                "Wedding Ceremony",
                "Banquet Halls / Resorts.....",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_dacor_icon,
                R.drawable.line_icon,
                "Dacor and Interior",
                "Chairs And Decoration / Flower.....",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_food_icon,
                R.drawable.line_icon,
                "Food and Drinks",
                "Food / Drinks",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_photographers_icon,
                R.drawable.line_icon,
                "Photographers",
                "Photographers / Pre wedding ......",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_hair_iocn,
                R.drawable.line_icon,
                "Hair Styler",
                "Bridal Hair Styler / Groom Hair....",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_weddingdress_icon,
                R.drawable.line_icon,
                "Wedding Dress",
                "Bridal Wedding Wear / Groom ....",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_honeymoonclothes_icon,
                R.drawable.line_icon,
                "Honeymoon Clothes",
                "Female Clothes / Male Clothes",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_gift_icon,
                R.drawable.line_icon,
                "General Gift",
                "Male Gifts / Female Gifts  / Gifts for ....",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_car_icon,
                R.drawable.line_icon,
                "Rent Car",
                "Car Rent / Car Decoration",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_bride_s_icon,
                R.drawable.line_icon,
                "Bride's Jewellery",
                "Gold / Diamond",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_bandwedding_icon,
                R.drawable.line_icon,
                "Wedding Band",
                "DJ / Band / Dancers / Wedding.....",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_travels_icon,
                R.drawable.line_icon,
                "Honeymoon Travels",
                "Local Travel / Outside Travel",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )
        serviceList.add(
            Services(
                1,
                R.drawable.ic_accessories_icon,
                R.drawable.line_icon,
                "Accessories",
                "Banquet Halls / Resorts.....",
                R.drawable.ic_button_right,
                list = arrayListOf()
            )
        )

    }


}
