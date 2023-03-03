package com.example.new_task.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.new_task.databinding.ChatLeftLayoutBinding
import com.example.new_task.databinding.ChatRightLayoutBinding
import com.example.new_task.entity.Chat
import com.example.new_task.entity.Customer

class ChatAdaptor(var context: Context,var chats:MutableList<Customer>):Adapter<ChatAdaptor.MyViewHolder>(){

    val MSG_TYPE_LEFT = 0
    val MSG_TYPE_RIGHT = 1

    inner class MyViewHolder(var binding :View): ViewHolder(binding) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       if (viewType==MSG_TYPE_LEFT){
           val left = ChatLeftLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
           return MyViewHolder(left.root)
       }else{
           val right = ChatRightLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
           return MyViewHolder(right.root)
       }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       var customer = chats[position]
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}