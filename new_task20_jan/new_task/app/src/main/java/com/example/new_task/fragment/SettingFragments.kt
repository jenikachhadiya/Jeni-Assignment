package com.example.new_task.fragment

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.new_task.R
import com.example.new_task.activity.DataActivity
import com.example.new_task.databinding.FragmentChatListBinding
import com.example.new_task.databinding.FragmentSettingFragmentsBinding


class SettingFragments : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSettingFragmentsBinding.inflate(LayoutInflater.from(requireContext()))

        binding.btnDataActivity.setOnClickListener {
            startActivity(Intent(requireContext(),DataActivity::class.java))
        }



        return binding.root
    }


}