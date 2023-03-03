package com.example.jetpackcomponent.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.jetpackcomponent.Modal.User
import com.example.jetpackcomponent.R
import com.example.jetpackcomponent.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()
            val user = User(email = email, pass = pass)
            //var action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(email)
            var action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            var navigation = Navigation.findNavController(binding.root).navigate(action)


        }

        binding.btnSignup.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_loginFragment_to_defaltFragment)
        /* Navigation.findNavController(binding.root)
                .navigate(R.id.action_loginFragment_to_registerFragment)*/
        }

    }




}