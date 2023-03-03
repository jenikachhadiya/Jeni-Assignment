package com.example.app.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.app.model.Electronic
import com.example.weddingapp.databinding.ElectronicLayoutBinding


class ElectronicAdapter(var context: Context, var electronicList: MutableList<Electronic>) :
    RecyclerView.Adapter<ElectronicAdapter.MyViewHolder>() {

    lateinit var binding: ElectronicLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ElectronicLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var electronic = electronicList[position]

        holder.bind.tvTitle.text = electronic.title
        holder.bind.rettingText.text = electronic.ratting.toString()
        holder.bind.ivStar.setImageResource(electronic.star)

        binding.ivThumbnail.load(electronicList[position].image) {
            crossfade(true)

        }
    }

    override fun getItemCount(): Int {
        return electronicList.size
    }

    class MyViewHolder(itemView: ElectronicLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }
}