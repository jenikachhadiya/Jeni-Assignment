package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Cloth
import com.example.weddingapp.databinding.ClothingLayoutBinding

class ClothingAdapter(var activity: Context, var clothList: MutableList<Cloth>) :
    RecyclerView.Adapter<ClothingAdapter.MyViewHolder>() {

    lateinit var binding: ClothingLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ClothingLayoutBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var cloth = clothList[position]

        holder.bind.ivDressName.text = cloth.name
        holder.bind.ivThumbnail.setImageResource(cloth.image)
        holder.bind.ivPrice.text = "$${cloth.price}"

    }

    override fun getItemCount(): Int {
        return clothList.size
    }

    class MyViewHolder(itemView: ClothingLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }
}