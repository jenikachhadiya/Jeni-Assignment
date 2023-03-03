package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Chat
import com.example.weddingapp.databinding.SearchLayoutBinding

class ChatAdapter(var context: Context, var chatList: MutableList<Chat>) :
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    lateinit var binding: SearchLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SearchLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val chat = chatList[position]

        holder.binding.ivProfileImage.setImageResource(chat.image)
        holder.binding.tvName.text = chat.name
        holder.binding.tvMessage.text = chat.text
        holder.binding.tvDot.setImageResource(chat.dot)
        holder.binding.tvMinit.text = chat.minte

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class MyViewHolder(val binding: SearchLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    fun filterList(filterlist: MutableList<Chat>) {
        this.chatList = filterlist
        notifyDataSetChanged()
    }
}