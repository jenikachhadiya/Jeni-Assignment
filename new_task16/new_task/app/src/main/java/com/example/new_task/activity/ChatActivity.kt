package com.example.new_task.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.adaptor.ChatAdaptor
import com.example.new_task.databinding.ActivityChatBinding
import com.example.new_task.entity.Chat
import com.example.new_task.entity.Customer
import com.example.new_task.entity.ReceiveData
import com.example.new_task.preference.PrefClass
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ChatActivity : AppCompatActivity() {


    lateinit var binding: ActivityChatBinding
    private lateinit var database: DatabaseReference
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var chatAdaptor: ChatAdaptor
    lateinit var chatList: ArrayList<ReceiveData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)




        var manger = PrefClass(this)
        database = Firebase.database.reference
        val getData = intent.getParcelableExtra<Chat>("chat")
        if (getData != null) {
            binding.tvName.text = getData.name
            binding.tvDetails.text = getData.detail

            Glide.with(applicationContext).load(getData.img).centerCrop()
                .placeholder(R.drawable.ic_baseline_sync_24).into(binding.ivImage)
        }


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
        chatList = ArrayList()
        chatAdaptor = ChatAdaptor(this, chatList)
        binding.recView.adapter = chatAdaptor
        binding.recView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }


    private fun readData(senderId: String, receiver: String) {
        database = FirebaseDatabase.getInstance().getReference("Chat")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {

                    val chat = dataSnapshot.getValue(ReceiveData::class.java)

                    if (chat != null) {
                        if (chat.senderId == senderId && chat.receiver == receiver ||
                            chat.senderId == receiver && chat.receiver == senderId
                        ) {
                            chatList.add(chat)
                            //chatAdaptor.setItem(chatList)
                            Log.e("ChatData", "onDataChange: $chat")
                        }
                        Log.e(TAG, "onDataSender: $senderId")
                        Log.e(TAG, "onDataReceiver: $receiver")
                        Log.e(TAG, "onDataMess: ${chat!!.message}")

                    }
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
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

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

    }
}