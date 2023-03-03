package com.example.new_task.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.new_task.R
import com.example.new_task.databinding.AutoSliderLayoutBinding
import com.example.new_task.entity.ImageSlider
import com.smarteist.autoimageslider.SliderViewAdapter

class AutoSliderAdaptor(var context: Context, var imgList: MutableList<ImageSlider>) :
    SliderViewAdapter<AutoSliderAdaptor.SliderViewHolder>() {


    override fun getCount(): Int {
        return imgList.size
    }

    // on below line we are creating a class for slider view holder.
    class SliderViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {

        // on below line we are creating a variable for our
        // image view and initializing it with image id.
        var imageView: ImageView = itemView!!.findViewById(R.id.auto_img)


    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
       val bind = AutoSliderLayoutBinding.inflate(LayoutInflater.from(context), parent, false)


        //val inflate: View = LayoutInflater.from(parent!!.context).inflate(R.layout.auto_slider_layout, null)
        return SliderViewHolder(bind.root)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        //if (viewHolder != null) {
          var img = imgList[position]

            if (viewHolder != null) {
                Glide
                    .with(context)
                    .load(img.img)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_sync_24)
                    .into(viewHolder.imageView)
            }


        }
    }


    /*override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        bind = AutoSliderLayoutBinding.inflate(LayoutInflater.from(context),container,false)

        var image = imgList[position]
      //  bind.ivImage.setImageResource(image.img)
        Glide
            .with(context)
            .load(image.img)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_sync_24)
            .into(bind.ivImage)


        container.addView(bind.root)

        return bind.root

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as RelativeLayout)
    }*/

