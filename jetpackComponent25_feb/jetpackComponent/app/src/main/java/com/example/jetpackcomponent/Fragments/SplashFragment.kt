package com.example.jetpackcomponent.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.jetpackcomponent.R

import com.example.jetpackcomponent.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       val binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            //shared elements
//            sharedElementEnterTransition = ChangeBounds().apply {
//                duration = 750
//            }
//            sharedElementReturnTransition = ChangeBounds().apply {
//                duration = 750
//            }
//            //com.example.jetpackcomponent.Fragments.LoginFragment







         //  val extras = FragmentNavigatorExtras(binding.root to "1")
            Navigation.findNavController(binding.root).navigate(R.id.action_splashFragment_to_loginFragment)
           // ActivityNavigator.applyPopAnimationsToPendingTransition(requireActivity())
        }, 2000)



        return binding.root
    }





}