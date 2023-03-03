package com.example.new_task.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.new_task.R
import com.example.new_task.databinding.ChatLeftLayoutBinding
import com.example.new_task.databinding.ChatRightLayoutBinding
import com.example.new_task.entity.ReceiveData
import com.example.new_task.preference.PrefClass

class ChatAdaptor(var context: Context, var chats: ArrayList<ReceiveData>) :
    Adapter<ChatAdaptor.MyViewHolder>() {

    private val MSG_TYPE_LEFT = 0
    private val MSG_TYPE_RIGHT = 1

    inner class MyViewHolder(view: View) : ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (viewType == MSG_TYPE_RIGHT) {
            val right = ChatRightLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
            return MyViewHolder(right.root)
        } else {
            val left = ChatLeftLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
            return MyViewHolder(left.root)
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var customer = chats[position]

        if (holder.javaClass == SendData::class.java) {
            val viewholder = holder as SendData
            holder.sendData.text = customer.message
        } else {
            val viewholder = holder as recData
            holder.receiveData.text = customer.message

        }


    }

    class SendData(itemView: View) : ViewHolder(itemView) {
        var sendData = itemView.findViewById<TextView>(R.id.tv_mess)
    }

    class recData(itemView: View) : ViewHolder(itemView) {
        var receiveData = itemView.findViewById<TextView>(R.id.tv_messleft)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun getItemViewType(position: Int): Int {
        var uid = PrefClass(context).getUserData().id
        if (chats[position].senderId == uid.toString()) {
            return MSG_TYPE_RIGHT
        } else {
            return MSG_TYPE_LEFT
        }

    }

    fun setItem(chatList: ArrayList<ReceiveData>) {
        this.chats = chatList
        notifyDataSetChanged()
    }
}