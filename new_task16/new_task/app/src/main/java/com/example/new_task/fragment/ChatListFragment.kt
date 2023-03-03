package com.example.new_task.fragment

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.new_task.R
import com.example.new_task.activity.ChatActivity
import com.example.new_task.adaptor.ChatListAdaptor
import com.example.new_task.clickInterface.OnItemClick
import com.example.new_task.databinding.FragmentChatListBinding
import com.example.new_task.entity.Chat
import com.example.new_task.listUnity.ImgList


class ChatListFragment : Fragment() {

    lateinit var chatListAdaptor: ChatListAdaptor
    private var chatlist = mutableListOf<Chat>()

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
        val binding = FragmentChatListBinding.inflate(LayoutInflater.from(requireContext()))

        var img = ImgList(requireContext())
        img.chatImg(chatlist)

        //chat
        chatListAdaptor = ChatListAdaptor(requireActivity(), chatlist)
        binding.recView.adapter = chatListAdaptor
        binding.recView.layoutManager = LinearLayoutManager(requireActivity())


        chatListAdaptor.onClickEvent(object :OnItemClick {
            override fun OnClickItemList(chat: Chat, pos: Int) {
                var intent = Intent(requireContext(), ChatActivity::class.java)
                intent.putExtra("chat",chat)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.fade_enter_from_left, R.anim.fade_enter_from_right)

            }

        })










        return (binding.root)
    }


}