package com.example.new_task.adaptor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.new_task.preference.PrefClass
import com.example.new_task.databinding.RecLayoutBinding
import com.example.new_task.entity.User

class RecAdaptor(var context: Context, var userlist: MutableList<User>) :
    Adapter<RecAdaptor.myviewholder>() {


    inner class myviewholder(var bind: RecLayoutBinding) : ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val binding = RecLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return myviewholder(binding)
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        val user = userlist[position]
        holder.bind.tvId.text = "${user.id}"
        holder.bind.tvName.text = user.name
        holder.bind.tvEmail.text = user.email

        val basetobit = user.img?.let {
            base64ToBitmap(it)
        }
        holder.bind.ivImage.setImageBitmap(basetobit)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    fun setItem(userItem: MutableList<User>) {
        this.userlist = userItem
        this.notifyDataSetChanged()

    }

    fun clearData() {
        var manager = PrefClass(context)
        manager.logout()
    }

   private fun base64ToBitmap(base64: String?): Bitmap?{
        val imageAsBytes: ByteArray = Base64.decode(base64?.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)

    }


}