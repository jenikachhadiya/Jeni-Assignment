package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Wedding
import com.example.weddingapp.databinding.CustomFristLayoutBinding

class WeddingAdapter(var activity: Context, var weddingList: MutableList<Wedding>) :
    RecyclerView.Adapter<WeddingAdapter.MyViewHolder>() {

    lateinit var binding: CustomFristLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = CustomFristLayoutBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var wedding = weddingList[position]

        holder.bind.tvName.text = wedding.name
        holder.bind.ivThumbnail.setImageResource(wedding.image)
        holder.bind.tvCountry.text = wedding.contryname
        holder.bind.tvPrice.text = "$${wedding.price}"
        holder.bind.ivStar.setImageResource(wedding.star)
        holder.bind.rettingText.text = wedding.ratting.toString()
    }

    override fun getItemCount(): Int {
        return weddingList.size
    }

    class MyViewHolder(itemView: CustomFristLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }
}