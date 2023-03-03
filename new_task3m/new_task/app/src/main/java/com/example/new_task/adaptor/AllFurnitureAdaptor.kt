package com.example.new_task.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.new_task.clickInterface.ClickListner
import com.example.new_task.R
import com.example.new_task.api.Get.modal.shopListGet
import com.example.new_task.databinding.AllfurnituresLayoutBinding
import com.example.new_task.entity.AllFurniture

class AllFurnitureAdaptor(var context: Context, var furniture: MutableList<shopListGet>) :
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
            .load(allfurniture.image)
            .placeholder(R.drawable.wait)
            .into(holder.bind.ivImage)
        holder.bind.tvTital.text = allfurniture.title
        holder.bind.tvRate.text = "${allfurniture.rating?.rate}"

       /* holder.bind.ivImage.setOnClickListener {
            clickListner.onClickList(allfurniture)
        }*/
    }
    fun setItem(userItem: MutableList<shopListGet>) {
        this.furniture = userItem.toMutableList()
        this.notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return furniture.size
    }
}