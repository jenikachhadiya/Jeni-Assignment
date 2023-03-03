package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Listitem
import com.example.app.model.Services
import com.example.weddingapp.R
import com.example.weddingapp.databinding.ServicesCustomLayoutBinding

class ServicesAdapter(var context: Context, var serviceList: MutableList<Services>) :
    RecyclerView.Adapter<ServicesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ServicesCustomLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val services = serviceList[position]


        holder.binding.ivThumbnail.setImageResource(services.image)
        holder.binding.ivLine.setImageResource(services.line)
        holder.binding.tvHedar.text = services.header
        holder.binding.tvName.text = services.name
        holder.binding.ivServicesButton.setImageResource(R.drawable.ic_button_right)
        holder.binding.ivServicesButton.setImageResource(R.drawable.ic_uper_arrow)



        holder.binding.relativeLayout.setOnClickListener {

            holder.binding.rcvSub.isVisible = !holder.binding.rcvSub.isVisible

        }


        services.list.clear()
        services.list.add(Listitem(R.drawable.ic_round, "Resorts"))
        services.list.add(Listitem(R.drawable.ic_round, "Function Hall"))
        services.list.add(Listitem(R.drawable.ic_round, "Resorts"))
        services.list.add(Listitem(R.drawable.ic_round, "Resorts"))
        services.list.add(Listitem(R.drawable.ic_round, "Hotels"))

        val adapter = ListitemServicesAdapter(context, services.list)
        holder.binding.rcvSub.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.binding.rcvSub.adapter = adapter


    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    class MyViewHolder(val binding: ServicesCustomLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


}