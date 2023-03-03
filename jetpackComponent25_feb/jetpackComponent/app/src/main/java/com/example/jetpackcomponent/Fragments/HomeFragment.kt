package com.example.jetpackcomponent.Fragments

import android.os.Bundle
import android.telecom.Call.Details
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import com.example.jetpackcomponent.Modal.User
import com.example.jetpackcomponent.R
import com.example.jetpackcomponent.databinding.FragmentHomeBinding
import com.example.jetpackcomponent.databinding.FragmentSplashBinding


class HomeFragment : Fragment() {

    val args: HomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        binding.tvResult.text = """
            Email = ${args.user}
        """.trimIndent()
        var navController = Navigation.findNavController(binding.root)
        binding.tvResult.setOnClickListener {

            navController.navigate(R.id.action_homeFragment_to_loginFragment)
        }
        //navigation drawer
       var appBarConfiguration = AppBarConfiguration(navController.graph)










        return binding.root
    }


}