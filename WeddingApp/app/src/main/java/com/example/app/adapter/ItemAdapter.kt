package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load

import com.example.app.model.Item
import com.example.weddingapp.R

import com.example.weddingapp.databinding.ItemLayoutBinding

class ItemAdapter(var Context: Context, var mainitemList: MutableList<Item>) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    lateinit var binding: ItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemLayoutBinding.inflate(LayoutInflater.from(Context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var item = mainitemList[position]

        holder.bind.tvItem.text =  item.text


        binding.ivItemImage.load(mainitemList[position].image) {
            crossfade(true)

        }

    }

    override fun getItemCount(): Int {
        return mainitemList.size
    }

    class MyViewHolder(itemView: ItemLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }
}