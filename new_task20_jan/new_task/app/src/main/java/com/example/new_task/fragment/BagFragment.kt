package com.example.new_task.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.new_task.preference.PrefClass
import com.example.new_task.databinding.FragmentBagBinding

class BagFragment : Fragment() {
    lateinit var binding:FragmentBagBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBagBinding.inflate(layoutInflater, container, false)

        PrefClass(requireActivity()).addToBagData(false)









        return binding.root
    }


}