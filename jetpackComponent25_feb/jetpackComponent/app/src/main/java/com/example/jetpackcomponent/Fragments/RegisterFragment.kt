package com.example.jetpackcomponent.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.jetpackcomponent.Modal.User
import com.example.jetpackcomponent.databinding.FragmentRegisterBinding
import com.example.jetpackcomponent.databinding.FragmentSplashBinding


class RegisterFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
      /* binding.btnSignup.setOnClickListener {
           val name = binding.etName.text.toString()
           val email = binding.etName.text.toString()
           val pass = binding.etName.text.toString()
           val phone = binding.etName.text.toString()
         var user= User(name = name, email = email, pass = pass, contect = phone)

           //navigation

           val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
           Navigation.findNavController(binding.root).navigate(action)





       }
*/




        return binding.root
    }


}