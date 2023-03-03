package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.databinding.ChatLeftLayoutBinding
import com.example.chatapp.databinding.ChatRightLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class ChatAdaptor(var context: Context, var chats: ArrayList<ChatRec>) :
    Adapter<RecyclerView.ViewHolder>() {

    val MSG_TYPE_LEFT = 0
    val MSG_TYPE_RIGHT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MSG_TYPE_LEFT) {
            val left = ChatLeftLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
            return RecViewHolder(left.root)
        } else {
            val right = ChatRightLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
            return SentViewHolder(right.root)
        }

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var chat = chats[position]
        if (holder.javaClass == SentViewHolder::class.java) {

            var viewHolder = holder as SentViewHolder
            viewHolder.sendMessage.text = chat.message

        } else {
            var viewHolder = holder as RecViewHolder
            viewHolder.recivedMessage.text = chat.message
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun getItemViewType(position: Int): Int {
        var chat = chats[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(chat.senderId)) {
            return MSG_TYPE_RIGHT
        } else {
            return MSG_TYPE_LEFT
        }
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sendMessage = itemView.findViewById<TextView>(R.id.tv_right)

    }

    class RecViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val recivedMessage = itemView.findViewById<TextView>(R.id.tv_left)

    }




}