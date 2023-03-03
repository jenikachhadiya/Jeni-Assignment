package com.example.jetpackcomponent.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.jetpackcomponent.R
import com.example.jetpackcomponent.databinding.FragmentDefaltBinding
import com.example.jetpackcomponent.databinding.FragmentRegisterBinding

class DefaltFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDefaltBinding.inflate(layoutInflater, container, false)

        binding.tvDefalt.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_defaltFragment_to_splashFragment)
        }
        return binding.root

    }


}