package com.gautam.validatonformgrewon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.databinding.IteamRecyclerBinding
import com.gautam.validatonformgrewon.databinding.RowItemsBinding
import com.gautam.validatonformgrewon.modal.ListRecyclerBurgurKing

class ListRecyclerAdepter(var context: Context, var list: ArrayList<ListRecyclerBurgurKing>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var Adition: TotalAmount

    interface TotalAmount {
        fun addamount(arraylist: ArrayList<ListRecyclerBurgurKing>, view: View)
        fun removeamount(arraylist: ArrayList<ListRecyclerBurgurKing>, view: View)
    }

    fun addAdition(adtion: TotalAmount) {
        this.Adition = adtion
    }

    class MyViewHolder(itemView: IteamRecyclerBinding) : RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }

    class RowHolder(itemView: RowItemsBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binde = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val bindings = RowItemsBinding.inflate(LayoutInflater.from(context), parent, false)
            return RowHolder(bindings)
        } else {
            val binding = IteamRecyclerBinding.inflate(LayoutInflater.from(context), parent, false)
            return MyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val blist = list[position]
        if (holder is MyViewHolder) {
            holder.bind.tvTitel.text = blist.title
            holder.bind.tvDescription.text = blist.description
            holder.bind.rattingPoint.text = blist.ratting.toString()
            holder.bind.tvPrice.text = blist.price.toString()

            Glide.with(context).load(blist.image).centerCrop().into(holder.bind.ivTmage)

            holder.bind.tvCount.text = "${blist.quantity}"

            holder.bind.tvAdd.setOnClickListener {
                blist.quantity += 1
                holder.bind.tvCount.text = "${blist.quantity}"
                showAddButton(holder, blist, true)
                val totalList = list.filter { it.quantity > 0 }
                Adition.addamount(ArrayList(totalList), it)
            }
            holder.bind.tvPlus.setOnClickListener {
                blist.quantity += 1
                holder.bind.tvCount.text = "${blist.quantity}"
                val totalList = list.filter { it.quantity > 0 }
                Adition.addamount(ArrayList(totalList), it)
            }

            holder.bind.tvMinus.setOnClickListener {
                if (blist.quantity != 0) {
                    blist.quantity -= 1
                }
                if (blist.quantity > 0) holder.bind.tvCount.text = "${blist.quantity}"
                showAddButton(holder, blist, false)
                val totalList = list.filter { it.quantity > 0 }
                Adition.removeamount(ArrayList(totalList), it)
            }
        } else if (holder is RowHolder) {
            Glide.with(context)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVBKE5SqZwvXw7vyMmUQAVG0mMnkLrokZ7Wg&usqp=CAU5++")
                .centerCrop().into(holder.binde.ivRowimage)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (list[position].tyepimage == "image") 1 else 2

    }

    override fun getItemCount(): Int {
        return list.size
    }


    private fun showAddButton(
        holder: MyViewHolder,
        blist: ListRecyclerBurgurKing,
        isAdd: Boolean
    ) {
        val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        val slidDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        val bottomToTop = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top)
        val slidBottom = AnimationUtils.loadAnimation(context, R.anim.slide_bottom)

        if (blist.quantity == 0) {
            holder.bind.tvAdd.visibility = View.VISIBLE
            holder.bind.lvCount.visibility = View.GONE
            holder.bind.lvCount.startAnimation(slidBottom)
            holder.bind.tvAdd.startAnimation(slidDown)
        } else {
            holder.bind.tvAdd.visibility = View.GONE
            holder.bind.lvCount.visibility = View.VISIBLE
            if (isAdd) {
                holder.bind.lvCount.startAnimation(bottomToTop)
                holder.bind.tvAdd.startAnimation(slideUp)
            }
        }
    }
}
