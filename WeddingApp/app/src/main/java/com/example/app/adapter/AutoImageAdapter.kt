package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import coil.load
import com.example.app.model.AutoSlider
import com.example.weddingapp.R

class AutoImageAdapter(var context: Context, var imageList:MutableList<AutoSlider>): PagerAdapter() {


    private var inflater:LayoutInflater = LayoutInflater.from(context)

     override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imaLayout = inflater.inflate(R.layout.row_item,container,false)

        var imageView=imaLayout.findViewById(R.id.ud_Avater) as ImageView


        imageView.load(imageList[position].image) {
            crossfade(true)

        }
        container.addView(imaLayout)
        return imaLayout

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View?)
        //super.destroyItem(container, position, `object`)
    }
}