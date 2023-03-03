package com.example.new_task.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.new_task.R
import com.example.new_task.databinding.ChatLeftLayoutBinding
import com.example.new_task.databinding.ChatRightLayoutBinding
import com.example.new_task.entity.ReceiveData
import com.example.new_task.preference.PrefClass
import com.google.firebase.auth.FirebaseAuth

class ChatAdaptor(var context: Context, var chats: ArrayList<ReceiveData>) :
    Adapter<RecyclerView.ViewHolder>() {

    private val MSG_TYPE_LEFT = 0
    private val MSG_TYPE_RIGHT = 1

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
            viewHolder.sendtime.text = chat.dateTime


        } else {
            var viewHolder = holder as RecViewHolder
            viewHolder.recivedMessage.text = chat.message
            viewHolder.receivetime.text = chat.dateTime
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
        val sendMessage = itemView.findViewById<TextView>(R.id.tv_mess)
        val sendtime = itemView.findViewById<TextView>(R.id.tv_send_time)

    }

    class RecViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val recivedMessage = itemView.findViewById<TextView>(R.id.tv_messleft)
        val receivetime = itemView.findViewById<TextView>(R.id.tv_rec_time)
    }
    /* lass ChatAdaptor(var context: Context, var chats: ArrayList<ChatRec>) :
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




}*/
}
