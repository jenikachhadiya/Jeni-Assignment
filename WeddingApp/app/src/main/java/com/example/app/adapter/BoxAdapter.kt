package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.app.model.Box
import com.example.weddingapp.R
import com.example.weddingapp.databinding.BoxBudgetBinding

class BoxAdapter(var activity: Context, var boxList: MutableList<Box>) :
    RecyclerView.Adapter<BoxAdapter.MyViewHolder>() {

    lateinit var binding: BoxBudgetBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = BoxBudgetBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var box = boxList[position]

        holder.bind.ivBox.setImageResource(R.drawable.ic_box)
        holder.bind.tvName.text = box.name
        holder.bind.tvPrice.text = "$${box.price}"

    }

    override fun getItemCount(): Int {
        return boxList.size
    }

    class MyViewHolder(itemView: BoxBudgetBinding) : RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }
}