package com.example.app.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.adapter.ChatAdapter

import com.example.app.model.Chat
import com.example.weddingapp.R
import com.example.weddingapp.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    var chatList = mutableListOf<Chat>()
    lateinit var chatadapter: ChatAdapter

    lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)

        preparaData()

        chatadapter = ChatAdapter(requireContext(), chatList)
        binding.rcvChatfragment.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvChatfragment.adapter = chatadapter


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


            }

            override fun afterTextChanged(s: Editable) {

                filter(s.toString())
            }
        })

        return binding.root

    }

    private fun filter(text: String?) {

        var filterChatList = mutableListOf<Chat>()

        for (item in chatList) {

            if (text != null) {
                if (item.name.lowercase().contains(text.lowercase())) {
                    filterChatList.add(item)
                }
            }
        }

        if (filterChatList.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            chatadapter.filterList(filterChatList)
        }
    }


    private fun preparaData() {

        chatList.add(
            Chat(
                1,
                R.drawable.ktv_image,
                "abc",
                "Out best price 10,000 for you",
                R.drawable.ic_dot_image,
                "2 Min ago"
            )
        )
        chatList.add(
            Chat(
                1,
                R.drawable.ktv_image,
                "TV Films",
                "Out best price 10,000 for you",
                R.drawable.ic_dot_image,
                "2 Min ago"
            )
        )
        chatList.add(
            Chat(
                1,
                R.drawable.ktv_image,
                "KTV Films",
                "Out best price 10,000 for you",
                R.drawable.ic_dot_image,
                "2 Min ago"
            )
        )
        chatList.add(
            Chat(
                1,
                R.drawable.ktv_image,
                "V Films",
                "Out best price 10,000 for you",
                R.drawable.ic_dot_image,
                "2 Min ago"
            )
        )
        chatList.add(
            Chat(
                1,
                R.drawable.ktv_image,
                "Films",
                "Out best price 10,000 for you",
                R.drawable.ic_dot_image,
                "2 Min ago"
            )
        )
        chatList.add(
            Chat(
                1,
                R.drawable.ktv_image,
                "KTV Films",
                "Out best price 10,000 for you",
                R.drawable.ic_dot_image,
                "2 Min ago"
            )
        )
        chatList.add(
            Chat(
                1,
                R.drawable.ktv_image,
                "KTV Films",
                "Out best price 10,000 for you",
                R.drawable.ic_dot_image,
                "2 Min ago"
            )
        )

    }


}