package com.example.anew

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.databinding.WhatsupRcLayBinding

class Wp_Ad(var context: Context,var wplist:MutableList<Wp>) :RecyclerView.Adapter<Wp_Ad.inerwp>() {

    private lateinit var binding: WhatsupRcLayBinding

    class inerwp(var bind: WhatsupRcLayBinding) :RecyclerView.ViewHolder(bind.root){
       // var bindview=bind
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): inerwp {
        binding = WhatsupRcLayBinding.inflate(LayoutInflater.from(context),parent,false)
        return inerwp(binding)
    }

    override fun onBindViewHolder(holder: inerwp, position: Int) {
       var wp = wplist[position]
        holder.bind.tvName.text = wp.name
        holder.bind.ivIg.setImageResource(wp.ig)
        holder.bind.tvMess.text=wp.mess
        holder.bind.rbRat.rating=wp.rating
        holder.bind.tvTime.text=wp.timedText

        holder.bind.linear.setOnClickListener {



        }

    }

    override fun getItemCount(): Int {
      return wplist.size
    }

}