package com.example.new_task.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.new_task.clickInterface.OnClickItem
import com.example.new_task.R
import com.example.new_task.databinding.ShopListLayoutBinding
import com.example.new_task.entity.ShopList

class ShopListAdaptor(var context: Context, var shopList: MutableList<ShopList>) :
    Adapter<ShopListAdaptor.MyViewHolder>() {

    lateinit var clickItem: OnClickItem

    inner class MyViewHolder(var bind: ShopListLayoutBinding) : ViewHolder(bind.root) {

    }
    fun onClickEvent(clickItem: OnClickItem) {
        this.clickItem = clickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ShopListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val shopList = shopList[position]
        Glide
            .with(context)
            .load(shopList.img)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_sync_24)
            .into(holder.bind.ivImage)
        holder.bind.tvName.text = shopList.shopName
        holder.bind.tvDetails.text = shopList.shopDetails
        holder.bind.tvAddress.text = shopList.address
        holder.bind.tvRate.text = shopList.ratNum
        holder.itemView.setOnClickListener {

            //Crashlytics
           // throw RuntimeException("Test Crash")
            clickItem.OnClickItemList(shopList, position)
        }

    }

    override fun getItemCount(): Int {
        return shopList.size
    }
}