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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ChatListFragment : Fragment() {

  /*  lateinit var chatListAdaptor: ChatListAdaptor
    private var chatlist = mutableListOf<Chat>()*/

  lateinit var chatListAdaptor: ChatListAdaptor
    private var chatlist = ArrayList<Chat>()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

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


        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference()

        chatlist = ArrayList()

       /* var data = ImgList(requireContext())
        data.chatListData(chatlist)*/


        chatListAdaptor = ChatListAdaptor(requireActivity(),chatlist)
        binding.recView.layoutManager = LinearLayoutManager(requireContext())
        binding.recView.adapter = chatListAdaptor

        db.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatlist.clear()

                for (data in snapshot.children){
                    val cureenuser = data.getValue(Chat::class.java)
                    if(mAuth.currentUser?.uid != cureenuser?.uid){
                        chatlist.add(cureenuser!!)
                    }

                }
                chatListAdaptor.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

       // var img = ImgList(requireContext())
      //  img.chatImg(chatlist)

        //chat
       // chatListAdaptor = ChatListAdaptor(requireActivity(), chatlist)
       // binding.recView.adapter = chatListAdaptor
       // binding.recView.layoutManager = LinearLayoutManager(requireActivity())


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