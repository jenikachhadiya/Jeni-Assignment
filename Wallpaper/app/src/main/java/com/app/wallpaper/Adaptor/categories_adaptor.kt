package com.app.wallpaper.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.wallpaper.Interface.OnCategoriesListner
import com.app.wallpaper.Modal.categories
import com.app.wallpaper.databinding.CategoriesLayoutBinding

class categories_adaptor(var context: Context, var catelist:MutableList<categories>)
    : RecyclerView.Adapter<categories_adaptor.innersee>() {

        lateinit var binding:CategoriesLayoutBinding
        lateinit var listner:OnCategoriesListner

        class innersee(var bind: CategoriesLayoutBinding) : RecyclerView.ViewHolder(bind.root){


        }
    fun catelistner(listnerr: OnCategoriesListner){
        this.listner = listnerr

    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): innersee {
            binding = CategoriesLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
            return innersee(binding)

        }


        override fun onBindViewHolder(holder: innersee, position: Int) {
            var cate = catelist[position]
            holder.bind.ivImg.setImageResource(cate.Img)
            holder.bind.tvTital.text = cate.Tital

            //image click listner
            holder.bind.ivImg.setOnClickListener {
                listner.categorieslistner(cate,position)
            }



        }

        override fun getItemCount(): Int {
            return catelist.size

        }


    }



