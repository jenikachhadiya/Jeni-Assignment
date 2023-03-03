package com.app.wallpaper.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.wallpaper.Interface.OnCategoriesListner
import com.app.wallpaper.Interface.OnImgDetailsListner
import com.app.wallpaper.Modal.categories
import com.app.wallpaper.Modal.imgD

import com.app.wallpaper.databinding.DetailImageLayoutBinding
import com.bumptech.glide.Glide

class imgdetali_adaptor (var context: Context, var imglist:MutableList<imgD>)
    :RecyclerView.Adapter<imgdetali_adaptor.myviewholder>() {


        lateinit var binding: DetailImageLayoutBinding
        lateinit var clickelistner :OnImgDetailsListner



      inner class myviewholder(var bind: DetailImageLayoutBinding) : RecyclerView.ViewHolder(bind.root){

          val imgview = bind.img

        }
    fun Clicked(imgDetailsListner: OnImgDetailsListner){
        this.clickelistner = imgDetailsListner
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
            binding = DetailImageLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
            return myviewholder(binding)

        }


        override fun onBindViewHolder(holder: myviewholder, position: Int) {
          //  var cate = imglist[position]
         //   holder.bind.img.setImageResource(cate.img)

            Glide.with(context).load(imglist[position].img).into(holder.imgview);


            holder.bind.img.setOnClickListener {
                clickelistner.OnImgListner(position)
            }


        }

        override fun getItemCount(): Int {
            return imglist.size

        }


    }




