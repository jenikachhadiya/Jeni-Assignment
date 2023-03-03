package com.example.new_task.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.clickInterface.OnItemClick
import com.example.new_task.databinding.ChatlistLayoutBinding
import com.example.new_task.entity.Chat

class ChatListAdaptor(var context: Context, var chatList: ArrayList<Chat>) :
    Adapter<ChatListAdaptor.MyViewHolder>() {

    lateinit var clickItem: OnItemClick

    inner class MyViewHolder(var bind: ChatlistLayoutBinding) : ViewHolder(bind.root) {

    }
    fun onClickEvent(clickItem: OnItemClick) {
        this.clickItem = clickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ChatlistLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val shopList = chatList[position]
        holder.bind.tvName.text = shopList.name


        holder.itemView.setOnClickListener {
            //Crashlytics
            clickItem.OnClickItemList(shopList,position)

        }

    }
    override fun getItemCount(): Int {
        return chatList.size
    }
}