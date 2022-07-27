package com.example.secondfregment

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.secondfregment.databinding.FragmentSecondFregmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFregment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFregment : Fragment() {
    lateinit var binding: FragmentSecondFregmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondFregmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            var name = it.getString("Name")
            var email = it.getString("Email")
            var age = it.getString("Age")

         binding.tvName.text = "Name = $name"
         binding.tvEmail.text = "Email = $email"
         binding.tvAge.text = "Age = $age"
        }

    }
}

