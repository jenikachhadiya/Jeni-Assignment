package com.gautam.validatonformgrewon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.databinding.FragmentShoppingBinding
import com.gautam.validatonformgrewon.modal.Shopping

class ShoppingAdepter(var context: Context, var shoplist: MutableList<Shopping>) :
    RecyclerView.Adapter<ShoppingAdepter.MyViewHolder>() {

    class MyViewHolder(itemView: FragmentShoppingBinding) : RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FragmentShoppingBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sho = shoplist[position]
        holder.bind.materialTextView.text = sho.text
        holder.bind.tvRatting.text = sho.ratting.toString()
        //   holder.bind.ivStar.setImageResource(sho.star)

        Glide.with(context)
            .load(sho.image)
            .centerCrop()
            .error(R.drawable.ic_no_image)
            .into(holder.bind.ivThumbnail)
    }

    override fun getItemCount(): Int {
        return shoplist.size
    }
}
