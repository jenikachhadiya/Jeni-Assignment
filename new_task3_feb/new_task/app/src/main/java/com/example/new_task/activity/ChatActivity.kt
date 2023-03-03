package com.example.new_task.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.new_task.adaptor.ChatAdaptor
import com.example.new_task.databinding.ActivityChatBinding
import com.example.new_task.entity.Chat
import com.example.new_task.entity.ReceiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    lateinit var chatAdaptor: ChatAdaptor
    lateinit var chatList: ArrayList<ReceiveData>

    private var senderRoom: String? = null
    private var recRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        database = FirebaseDatabase.getInstance().reference

        val getData = intent.getParcelableExtra<Chat>("chat")
        binding.tvName.text = getData?.name
        val receivedUid = getData?.uid

        val senderId = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receivedUid + senderId
        recRoom = senderId + receivedUid
        chatList = ArrayList()
        chatAdaptor = ChatAdaptor(this, chatList)

        binding.recView.layoutManager = LinearLayoutManager(this)
        binding.recView.adapter = chatAdaptor


        database.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    chatList.clear()
                    for (data in snapshot.children) {
                            val message = data.getValue(ReceiveData::class.java)
                            chatList.add(message!!)


                    }
                    binding.recView.scrollToPosition(chatList.size - 1)
                    chatAdaptor.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        binding.btnEnter.setOnClickListener {
            val message = binding.etMess.text.toString().trim()
            val date = Date()
             val format = SimpleDateFormat("EEE, MMM dd hh:mm a")
            val dateandtime = format.format(date)
            Log.e(TAG, "onCreate: $date")
            Log.e(TAG, "onCreate: $dateandtime")
                 if (message.isNotEmpty()){
                     val chatRecObject = ReceiveData(senderId, message,dateandtime)
                     database.child("chats").child(senderRoom!!).child("messages").push()
                         .setValue(chatRecObject).addOnSuccessListener {
                             database.child("chats").child(recRoom!!).child("messages").push()
                                 .setValue(chatRecObject)
                         }
                 }
            binding.etMess.setText("")

        }


/*
        firebaseDatabase = FirebaseDatabase.getInstance()
        database = firebaseDatabase.getReference(manger.getUserData().id.toString())
            .child(getData!!.id.toString())

        Log.e("PrefData", "onCreate: ${manger.getUserData()}")

        var userid = PrefClass(this).getUserData().id.toString()
        //Enter Data
        binding.btnEnter.setOnClickListener {
            val message = binding.etMess.text.toString().trim()
            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "PlZ Enter Some Data", Toast.LENGTH_SHORT).show()
            } else {
                sendMessage(userid, getData.id.toString(), message)
                addDataForFirebase(message)
                // chatList.add(ReceiveData(userid, getData.id.toString(), message))
                binding.etMess.setText("")
            }
        }
        //GetData
        readData(userid, getData.id.toString())
        *//* chatList = ArrayList()
         chatAdaptor = ChatAdaptor(this, chatList)
         binding.recView.adapter = chatAdaptor
         binding.recView.layoutManager =
             LinearLayoutManager(this, RecyclerView.VERTICAL, false)*//*

    }


    private fun readData(senderId: String, receiver: String) {
        database = FirebaseDatabase.getInstance().getReference("ChatApp")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {

                    //    val chat = dataSnapshot.getValue(ReceiveData::class.java)


                    try {
                        Log.e(
                            TAG,
                            "onDataSender: ${dataSnapshot.getValue(ReceiveData::class.java)}"
                        )
                    } catch (E: Exception) {

                    }


//                    if (chat != null){
//                        if (chat.senderId == senderId && chat.receiver == receiver ||
//                            chat.senderId == receiver && chat.receiver == senderId
//                        ) {
//                          //  chatList.add(chat)
//                            //chatAdaptor.setItem(chatList)
//                            Log.e("ChatData", "onDataChange: $chat")
//                        }
//                        Log.e(TAG, "onDataSender: $senderId")
//                        Log.e(TAG, "onDataReceiver: $receiver")
//                        Log.e(TAG, "onDataMess: ${chat!!.message}")
//                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "onCancelled", Toast.LENGTH_SHORT).show()
            }

        })


    }

    //send data
    private fun addDataForFirebase(message: String) {
        var cus = Customer(message)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                database.setValue(cus)
                Toast.makeText(applicationContext, "addData", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "failData", Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun sendMessage(senderId: String, receiver: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference("ChatApp")

        var hashMap: HashMap<String, String> = HashMap()
        hashMap["senderId"] = senderId.toString()
        hashMap["receiver"] = receiver
        hashMap["message"] = message

        reference!!.child("Chat").push().setValue(hashMap)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)

    }*/
    }
}