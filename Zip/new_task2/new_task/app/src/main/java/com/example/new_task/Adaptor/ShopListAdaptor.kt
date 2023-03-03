package com.example.new_task.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.new_task.Interface.OnClickItem
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
        val shoplist = shopList[position]
        Glide
            .with(context)
            .load(shoplist.img)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_sync_24)
            .into(holder.bind.ivImage)
        holder.bind.tvName.text = shoplist.shopName
        holder.bind.tvDetails.text = shoplist.shopDetails
        holder.bind.tvAddress.text = shoplist.address
        holder.bind.tvRate.text = shoplist.ratNum
        holder.itemView.setOnClickListener {
            clickItem.OnClickItemList(shoplist, position)
        }

    }

    override fun getItemCount(): Int {
        return shopList.size
    }
}