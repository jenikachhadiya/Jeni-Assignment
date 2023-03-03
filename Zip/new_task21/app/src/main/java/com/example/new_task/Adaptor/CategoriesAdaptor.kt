package com.example.new_task.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.databinding.CategoriesLayoutBinding
import com.example.new_task.entity.Category

class CategoriesAdaptor(var context: Context,var categoryList:MutableList<Category>):Adapter<CategoriesAdaptor.MyViewHolder>(){
    lateinit var binding: CategoriesLayoutBinding

    inner class MyViewHolder(var bind :CategoriesLayoutBinding):ViewHolder(bind.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       binding = CategoriesLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = categoryList[position]
        Glide
            .with(context)
            .load(category.Img)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_sync_24)
            .into(binding.ivImage)

    }

    override fun getItemCount(): Int {
       return  categoryList.size
    }
}