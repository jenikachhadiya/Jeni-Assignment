package com.example.new_task.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.api.post.modal.AutoSliderGet
import com.example.new_task.databinding.AutoSliderLayoutBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class AutoSliderAdaptor(var context: Context, var imgList: MutableList<AutoSliderGet>) :
    SliderViewAdapter<AutoSliderAdaptor.SliderViewHolder>() {


    override fun getCount(): Int {
        return imgList.size
    }

    class SliderViewHolder(var itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {
        var imageView: ImageView = itemView!!.findViewById(R.id.auto_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val bind = AutoSliderLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return SliderViewHolder(bind.root)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        //if (viewHolder != null) {
        val img = imgList[position]
        if (viewHolder != null) {
            Glide
                .with(context)
                .load(img.image)
                .placeholder(R.drawable.wait)
                .into(viewHolder.imageView)
        }

    }

    fun setItem(userItem: MutableList<AutoSliderGet>?) {
        if (userItem != null) {
            this.imgList = userItem
        }
        this.notifyDataSetChanged()

    }
}


