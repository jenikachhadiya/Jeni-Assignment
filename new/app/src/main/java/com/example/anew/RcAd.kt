package com.example.anew

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.databinding.RcItemLayBinding

class RcAd(var context: Context,var itemlist:MutableList<Rc>):RecyclerView.Adapter<RcAd.inRc>() {

    lateinit var binding: RcItemLayBinding

    class inRc(var bind: RcItemLayBinding) :RecyclerView.ViewHolder(bind.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): inRc {
       binding = RcItemLayBinding.inflate(LayoutInflater.from(context),parent,false)
        return inRc(binding)
    }

    override fun onBindViewHolder(holder: inRc, position: Int) {
        var it = itemlist[position]
        holder.bind.tvTit.text = it.tit
        holder.bind.ivImg.setImageResource(it.img)

    }

    override fun getItemCount(): Int {
       return itemlist.size
    }
}