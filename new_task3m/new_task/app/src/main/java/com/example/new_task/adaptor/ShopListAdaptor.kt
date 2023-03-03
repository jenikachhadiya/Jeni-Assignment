package com.example.new_task.adaptor


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.api.Get.modal.shopListGet
import com.example.new_task.api.post.modal.ApiUser
import com.example.new_task.clickInterface.OnClickItem
import com.example.new_task.databinding.BannerLayoutBinding
import com.example.new_task.databinding.ShopListLayoutBinding


class ShopListAdaptor(var context: Context, var shopList: MutableList<shopListGet?>) :
    Adapter<RecyclerView.ViewHolder>() {


    lateinit var clickItem: OnClickItem

    private val itemView = 0
    private val bannerView = 1

    class MyViewHolder1(var item: ShopListLayoutBinding) : ViewHolder(item.root) {

    }

    class MyViewHolder2(var banner: BannerLayoutBinding) : ViewHolder(banner.root) {

    }

    fun onClickEvent(clickItem: OnClickItem) {
        this.clickItem = clickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == bannerView) {
            val banner = BannerLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
            return MyViewHolder2(banner)
        } else {
            val item = ShopListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
            return MyViewHolder1(item)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopList = shopList[position]
        if (holder is MyViewHolder1) {
            val viewHolder = holder
         /*  if (shopList != null) {
                Glide
                    .with(context)
                    .load(shopList.img)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_sync_24)
                    .into(viewHolder.item.ivImage)
            }
            viewHolder.item.tvName.text = shopList?.shopName
            viewHolder.item.tvDetails.text = shopList?.shopDetails
            viewHolder.item.tvAddress.text = shopList?.address
            viewHolder.item.tvRate.text = shopList?.ratNum
            Log.e("viewHolder", "onBindViewHolder: ${shopList?.shopName}", )*/
            if (shopList != null) {
                Glide
                    .with(context)
                    .load(shopList.image)
                    .placeholder(R.drawable.ic_baseline_sync_24)
                    .into(viewHolder.item.ivImage)
            }
            viewHolder.item.tvName.text = shopList?.title
            viewHolder.item.tvDetails.text = shopList?.description
            viewHolder.item.tvAddress.text = shopList?.category
            viewHolder.item.tvRate.text = shopList?.rating?.rate.toString()
            viewHolder.item.rate.rating = shopList?.rating?.rate!!.toFloat()


            holder.itemView.setOnClickListener {
                clickItem.OnClickItemList(shopList.id!!, position)
            }
        } else {
           /* var viewHolder = holder as MyViewHolder2
            Glide
                .with(context)
                .load(shopList?.banner)
                .centerCrop()
                .into(viewHolder.banner.ivImg)*/


        }
    }

    fun setItem(userItem: ArrayList<shopListGet?>) {
        this.shopList = userItem.toMutableList()
        this.notifyDataSetChanged()

    }
    fun dataAdd(userItem: ArrayList<shopListGet?>) {
        this.shopList.addAll(userItem)
        this.notifyDataSetChanged()

    }


    override fun getItemCount(): Int {
        return shopList.size
    }
    fun dataList(oldList:ArrayList<shopListGet>,newList:ArrayList<shopListGet>):Boolean{
        return oldList==newList
        notifyDataSetChanged()
    }

    /* override fun getItemViewType(position: Int): Int {
         if (shopList[position]?.viewType == 0) {
             return itemView
         }
         return bannerView

     }
 */

}