package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Hedar
import com.example.weddingapp.databinding.HedarBudgetBinding

class HedarAdapter(var activity: Context, var hedarList: MutableList<Hedar>) :
    RecyclerView.Adapter<HedarAdapter.MyViewHolder>() {

    lateinit var binding: HedarBudgetBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = HedarBudgetBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var hedar = hedarList[position]

        holder.bind.tvHedar.text = hedar.hedar

        var adapter = BoxAdapter(activity, hedar.budgetList)
        binding.rcvBoxBudget.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rcvBoxBudget.adapter = adapter


    }

    override fun getItemCount(): Int {
        return hedarList.size
    }

    class MyViewHolder(itemView: HedarBudgetBinding) : RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }

    fun filterList(filterlist: MutableList<Hedar>) {
        this.hedarList = filterlist
        notifyDataSetChanged()
    }
}