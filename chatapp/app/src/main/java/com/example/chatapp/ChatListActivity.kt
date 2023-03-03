package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatListBinding
import com.example.new_task.activity.ChatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ChatListActivity : AppCompatActivity() {
    //    lateinit var binding: ActivityChatListBinding
//
   lateinit var chatListAdaptor: ChatListAdaptor
   private var chatlist = ArrayList<Chat>()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference()

        chatlist = ArrayList()
        chatListAdaptor = ChatListAdaptor(this,chatlist)

        binding.recView.layoutManager = LinearLayoutManager(this)
        binding.recView.adapter = chatListAdaptor

        db.child("user").addValueEventListener(object :ValueEventListener{
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


//       chatImg(chatlist)
//
//        //chat
//        chatListAdaptor = ChatListAdaptor(this, chatlist)
//        binding.recView.adapter = chatListAdaptor
//        binding.recView.layoutManager = LinearLayoutManager(this)
//
//      //  getUsersList()
//
        chatListAdaptor.onClickEvent(object :OnItemClick {
            override fun OnClickItemList(chat: Chat, pos: Int) {
                var intent = Intent(applicationContext, ChatActivity::class.java)
                intent.putExtra("chat", chat)
                startActivity(intent)


            }
        })
//
//
//    }
//    fun chatImg(chatList: MutableList<Chat>){
//        chatList.add(Chat(1,"https://img.freepik.com/free-vector/retro-furniture-logo_23-2148464123.jpg?w=740&t=st=1672036336~exp=1672036936~hmac=e6330984d1217f2d60451d83dbac8537d5556aea8b673c59464ba3496a07e4d8","Luxurious Lumber","furniture,  ...",))
//        chatList.add(Chat(2,"https://img.freepik.com/free-vector/minimalist-furniture-logo-template-design_23-2148467616.jpg?w=740&t=st=1672036357~exp=1672036957~hmac=2fa016f51e352d283394ef2d63f313a999ca5c2d9728e00b30f19e50dab08725","Luxury Leather Chairs","furniture,  ..."))
//        chatList.add(Chat(3,"https://img.freepik.com/free-vector/furniture-logo-concept_23-2148619623.jpg?w=740&t=st=1672036394~exp=1672036994~hmac=d71d26712779c0a2c0e382ba8d19b96082d0624b5a675e1736cad4ec2ef80970","Meoble’s & Marbles","furniture,different ..."))
//        chatList.add(Chat(4,"https://img.freepik.com/free-vector/furniture-logo-with-minimalist-elements_23-2148628260.jpg?w=740&t=st=1672036453~exp=1672037053~hmac=658c4a7467134c3575910f06c8e8c97f91be03f9511360c94c9a8cf000f29adb","Minimal Home","furniture,  different ..."))
//        chatList.add(Chat(5,"https://img.freepik.com/free-vector/retro-furniture-logo_23-2148452344.jpg?w=740&t=st=1672036474~exp=1672037074~hmac=e40d30bebe5ec1538fae2077d8a359cb913cd33d7d479f56f0f2796dbea21253","Oakley","furniture,  different ..."))
//        chatList.add(Chat(6,"https://img.freepik.com/free-vector/elegant-furniture-logo-template_23-2148470895.jpg?w=740&t=st=1672036499~exp=1672037099~hmac=6f39f170d93736b042f21c8290c6a551daaf065b0f242df1005c472c7a61f000","Odense Timber","furniture, household  ..."))
//        chatList.add(Chat(7,"https://img.freepik.com/free-vector/minimalist-furniture-logo-template-with-illustration_23-2148619518.jpg?w=740&t=st=1672036527~exp=1672037127~hmac=7036b4c074fbe3e04d480fd83039a4f60b0d1faaaf076ee8b7795f5e7eb0af45","Platinum Furniture","furniture, household  different ..."))
//        chatList.add(Chat(8,"https://img.freepik.com/free-vector/furniture-company-logo-business-template-branding-design-xx-home-interior_53876-140604.jpg?w=740&t=st=1672036554~exp=1672037154~hmac=163512a021544a8c49b09208410d131e471a812be59463f39ca95b414422312c","Premium Store","furniture,  different ..."))
//        chatList.add(Chat(9,"https://img.freepik.com/free-vector/retro-furniture-logo-template-theme_23-2148467615.jpg?w=740&t=st=1672036586~exp=1672037186~hmac=d68013421e6e0b13fd8e33bb4e86f735dec63d668042bb8ba5bf05b66cf41b3c","Prescott Fusion","furniture,  different ..."))
//        chatList.add(Chat(10,"https://img.freepik.com/free-vector/furniture-logo-concept_23-2148629242.jpg?w=740&t=st=1672036617~exp=1672037217~hmac=687728913d3c4cedca520e7c997a0514321da1de560437242dfee71607150ae2","Quality Construct","furniture, household  different ..."))
//    }
//    fun getUsersList() {
//        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
//        var userid = firebase.uid
//        val databaseReference: DatabaseReference =
//            FirebaseDatabase.getInstance().getReference("Users")
//
//
//        databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                val currentUser = snapshot.getValue(Chat::class.java)
//                for (dataSnapShot: DataSnapshot in snapshot.children) {
//                    val user = dataSnapShot.getValue(Chat::class.java)
//
//                    if (!user!!.id.equals(firebase.uid)) {
//                        chatlist.add(user)
//                    }
//                }
//
//
//            }
//
//        })
//    }

    }
}