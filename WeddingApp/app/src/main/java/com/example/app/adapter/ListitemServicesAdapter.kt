package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Listitem
import com.example.weddingapp.R
import com.example.weddingapp.databinding.ListitenServicesLayoutBinding

class ListitemServicesAdapter(val context: Context, var listeitemlist: ArrayList<Listitem>) :
    RecyclerView.Adapter<ListitemServicesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListitenServicesLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val listitem = listeitemlist[position]

        holder.binding.listRound.setImageResource(R.drawable.ic_dot)
        holder.binding.listText.text = listitem.text


    }

    override fun getItemCount(): Int {
        return listeitemlist.size
    }

//    fun setItems(list: ArrayList<Listitem>)
//    {
//        this.listeitemlist = list
//        notifyDataSetChanged()
//
//    }

    class MyViewHolder(val binding: ListitenServicesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}