package com.example.new_task.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.databinding.ActivityChatBinding
import com.example.new_task.entity.Chat
import com.example.new_task.entity.Customer
import com.example.new_task.entity.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ChatActivity : AppCompatActivity() {


    private lateinit var database: DatabaseReference

    lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference


        var getData = intent.getParcelableExtra<Chat>("chat")
        if (getData != null) {
            binding.tvName.text = "${getData.name}"
            binding.tvDetails.text = "${getData.detail}"

            Glide.with(applicationContext).load(getData.img).centerCrop()
                .placeholder(R.drawable.ic_baseline_sync_24).into(binding.ivImage)
        }

        firebaseDatabase = FirebaseDatabase.getInstance()
        database = firebaseDatabase.getReference(getData!!.name).child(getData.id.toString())

        //Enter Data
        binding.btnEnter.setOnClickListener {

           // var name = binding.etName.text.toString().trim()
          //  var email = binding.etEmail.text.toString().trim()

            var message = binding.etMess.text.toString().trim()
            sendMessage(getData!!.id,getData.name,message)

/*
            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "PlZ Enter Some Data", Toast.LENGTH_SHORT).show()
            } else {
                addDataForFirebase(name, email)
            }*/

            if (TextUtils.isEmpty(message)){
                Toast.makeText(applicationContext, "PlZ Enter Some Data", Toast.LENGTH_SHORT).show()
            }else{
                addDataForFirebase(message)
            }


        }

        //GetData

        database.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                var value = snapshot.getValue(Customer::class.java)
              //  binding.tvMess1.text = value.toString()
                Toast.makeText(applicationContext, "getData", Toast.LENGTH_SHORT).show()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "failed data", Toast.LENGTH_SHORT).show()
            }

        })




    }

    //send data
    private fun addDataForFirebase(message:String) {
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
    private fun sendMessage(senderId: Int, receiver:String, message: String){
        var reference:DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap :HashMap<String,String> = HashMap()
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