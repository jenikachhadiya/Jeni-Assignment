package com.example.task


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.new_task.entity.ShopList
import com.example.task.R
import com.example.task.databinding.RecentviewLayoutBinding

class RecentviewAdaptor(var context: Context, var recentList: MutableList<ShopList>) :
    Adapter<RecentviewAdaptor.MyViewHolder>() {
    var itemCounter: OnClickCount? = null

    inner class MyViewHolder(var bind: RecentviewLayoutBinding) : ViewHolder(bind.root)

    fun addItemCounter(itemConter:OnClickCount) {
        this.itemCounter = itemConter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecentviewLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recent = recentList[position]

        Glide
            .with(context)
            .load(recent.img)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_timer_24)
            .into(holder.bind.ivImage)
        holder.bind.tvName.text = recent.shopName
        holder.bind.tvDetails.text = recent.shopDetails
        holder.bind.tvRate.text = recent.ratNum
        holder.bind.rate.rating = recent.rating
        holder.bind.tvPrice.text = "${recent.price}"

        val animationUp =
            AnimationUtils.loadAnimation(context, R.anim.slide_up)
        val animationBottomToTop =
            AnimationUtils.loadAnimation(context, R.anim.slide_bottomtotop)

        holder.bind.tvPlus.setOnClickListener {
            recent.counter+=1
            holder.bind.tvCounter.text = "${recent.counter}"
            val list = recentList.filter { it.counter > 0 }
            itemCounter?.incrementDecrement(it, ArrayList(list))
        }
        holder.bind.tvMinus.setOnClickListener {
            if (recent.counter > 0) {
                recent.counter-=1
                holder.bind.tvCounter.text = "${recent.counter}"
                val list = recentList.filter { it.counter > 0 }
                itemCounter?.incrementDecrement(it, ArrayList(list))
            } else{
                    holder.bind.layoutCounter.startAnimation(animationUp)
                    holder.bind.layoutCounter.visibility = View.GONE
                    holder.bind.btnAdd.startAnimation(animationBottomToTop)
                    holder.bind.btnAdd.visibility = View.VISIBLE
            }
        }
        holder.bind.layoutCounter.visibility = View.GONE
        holder.bind.btnAdd.setOnClickListener {
            it.startAnimation(animationUp)
            holder.bind.btnAdd.visibility = View.GONE
            recent.counter += 1
            holder.bind.tvCounter.text = "${recent.counter}"
            var list = recentList.filter { it.counter > 0 }
            itemCounter?.incrementDecrement(it, ArrayList(list))
            holder.bind.layoutCounter.startAnimation(animationBottomToTop)
            holder.bind.layoutCounter.visibility = View.VISIBLE
            itemCounter?.OnClickItem(it, recent, position)

        }
    }

    override fun getItemCount(): Int {
        return recentList.size
    }


}


