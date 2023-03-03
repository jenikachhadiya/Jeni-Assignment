package com.example.new_task.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.Chat
import com.example.chatapp.ChatAdaptor
import com.example.chatapp.ChatRec
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.new_task.entity.ReceiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ChatActivity : AppCompatActivity() {

    //
//    lateinit var binding: ActivityChatBinding
    private lateinit var database: DatabaseReference
    //lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var chatAdaptor: ChatAdaptor
    lateinit var chatList: ArrayList<ChatRec>

    var senderRoom: String? = null
    var recRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference()
//        database = Firebase.database.reference
        val getData = intent.getParcelableExtra<Chat>("chat")
        binding.tvName.text = getData?.name
        var receivedUid = getData?.uid

        val senderid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receivedUid+senderid
        recRoom = senderid+receivedUid
        chatList = ArrayList()
        chatAdaptor = ChatAdaptor(this, chatList)

        binding.recView.layoutManager = LinearLayoutManager(this)
        binding.recView.adapter = chatAdaptor

        database.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    chatList.clear()
                    for (data in snapshot.children){
                        val message = data.getValue(ChatRec::class.java)
                            chatList.add(message!!)
                    }
                    chatAdaptor.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })




        binding.btnEnter.setOnClickListener {
            val message = binding.etMess.text.toString().trim()
            val chatrecObject = ChatRec(senderid,message)
            database.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(chatrecObject).addOnSuccessListener {
                    database.child("chats").child(recRoom!!).child("messages").push()
                        .setValue(chatrecObject)
                }
            binding.etMess.setText("")

        }


//            Glide.with(applicationContext).load(getData.img).centerCrop()
//                .placeholder(R.drawable.common_full_open_on_phone).into(binding.ivImage)
//        }
//
//
//        firebaseDatabase = FirebaseDatabase.getInstance()
//        database = firebaseDatabase.getReference(getData!!.id.toString())
//            .child(getData!!.id.toString())
//
//      //  var userid = PrefClass(this).getUserData().id.toString()
//        //Enter Data
//        binding.btnEnter.setOnClickListener {
//            val message = binding.etMess.text.toString().trim()
//            if (message.isEmpty()) {
//                Toast.makeText(applicationContext, "PlZ Enter Some Data", Toast.LENGTH_SHORT).show()
//            } else {
//                sendMessage(getData.id.toString(), getData.name, message)
//                addDataForFirebase(message)
//                // chatList.add(ReceiveData(userid, getData.id.toString(), message))
//                binding.etMess.setText("")
//            }
//
//
//        }
//        //GetData
//        readData(getData.id.toString(), getData.id.toString())
//        chatList = ArrayList()
//       // chatAdaptor = ChatAdaptor(this, chatList)
//        binding.recView.adapter = chatAdaptor
//        binding.recView.layoutManager =
//            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//
//    }
//
//
//    private fun readData(senderId: String, receiver: String) {
//        database = FirebaseDatabase.getInstance().getReference("Chat")
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (dataSnapshot: DataSnapshot in snapshot.children) {
//
//                    val chat = dataSnapshot.getValue(ReceiveData::class.java)
//
//                    if (chat != null) {
//                        if (chat.senderId == senderId && chat.receiver == receiver ||
//                            chat.senderId == receiver && chat.receiver == senderId
//                        ) {
//                            chatList.add(chat)
//                            //chatAdaptor.setItem(chatList)
//                            Log.e("ChatData", "onDataChange: $chat")
//                        }
//                        Log.e(TAG, "onDataSender: $senderId")
//                        Log.e(TAG, "onDataReceiver: $receiver")
//                        Log.e(TAG, "onDataMess: ${chat!!.message}")
//
//                    }
//                }
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, "onCancelled", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
//
//    }
//
//    //send data
//    private fun addDataForFirebase(message: String) {
//      //  var cus = Customer(message)
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//               // database.setValue(cus)
//                Toast.makeText(applicationContext, "addData", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(applicationContext, "failData", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
//
//    }
//
//    private fun sendMessage(senderId: String, receiver: String, message: String) {
//        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()
//
//        var hashMap: HashMap<String, String> = HashMap()
//        hashMap["senderId"] = senderId.toString()
//        hashMap["receiver"] = receiver
//        hashMap["message"] = message
//
//        reference!!.child("Chat").push().setValue(hashMap)
//
//
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        finish()
//
//    }
    }
}