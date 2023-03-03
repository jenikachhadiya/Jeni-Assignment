package com.example.new_task.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.new_task.Interface.ClickListner
import com.example.new_task.R
import com.example.new_task.databinding.AllfurnituresLayoutBinding
import com.example.new_task.entity.AllFurniture

class AllFurnitureAdaptor(var context: Context, var furniture: MutableList<AllFurniture>) :
    Adapter<AllFurnitureAdaptor.MyViewHolder>() {

    lateinit var clickListner: ClickListner

    inner class MyViewHolder(var bind: AllfurnituresLayoutBinding) : ViewHolder(bind.root) {

    }

    fun onClickEvent(clickListner: ClickListner) {
        this.clickListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            AllfurnituresLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var allfurniture = furniture[position]
        Glide
            .with(context)
            .load(allfurniture.Img)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_sync_24)
            .into(holder.bind.ivImage)
        holder.bind.tvTital.text = allfurniture.Tital
        holder.bind.tvRate.text = "${allfurniture.Rate}"
        holder.bind.ivImage.setOnClickListener {
            clickListner.onClickList(allfurniture)
        }
    }

    override fun getItemCount(): Int {
        return furniture.size
    }
}