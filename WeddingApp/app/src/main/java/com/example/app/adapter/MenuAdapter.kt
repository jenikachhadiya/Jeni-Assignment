package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Menu
import com.example.weddingapp.databinding.MenuLayoutBinding


class MenuAdapter(var context: Context, var menuList: ArrayList<Menu>) :
    RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {

    lateinit var communicator: Communicator


    interface Communicator {

        fun addAmount(arrayList: ArrayList<Menu>)
        fun removeAmount(arrayList: ArrayList<Menu>)

    }

    fun addCommunicator(communicator: Communicator) {
        this.communicator = communicator
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MenuLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val menu = menuList[position]
        holder.bind.tvMenu.text = menu.name
        holder.bind.tvPrice.text = "$${menu.price}"
        holder.bind.tvConuting.text = "${menu.quantity}"
        showAddButton(holder, menu)


        holder.bind.btnCart.setOnClickListener {
            menu.quantity += 1
            holder.bind.tvConuting.text = "${menu.quantity}"
            val list = menuList.filter { it.quantity > 0 }
            showAddButton(holder, menu)
            communicator.addAmount(ArrayList(list))
        }

        holder.bind.tvIncrement.setOnClickListener {
            menu.quantity += 1
            holder.bind.tvConuting.text = "${menu.quantity}"
            val list = menuList.filter { it.quantity > 0 }
            showAddButton(holder, menu)
            communicator.addAmount(ArrayList(list))
        }

        holder.bind.tvDecrement.setOnClickListener {
            if (menu.quantity != 0)
                menu.quantity -= 1
            holder.bind.tvConuting.text = "${menu.quantity}"
                val list = menuList.filter { it.quantity > 0 }
            showAddButton(holder, menu)
            communicator.removeAmount(ArrayList(list))
        }


    }

    private fun showAddButton(holder: MyViewHolder, menu: Menu) {


        if (menu.quantity > 0){
            holder.bind.layoutCart.visibility = View.GONE
            holder.bind.layoutQuantity.visibility = View.VISIBLE

        }else{
            holder.bind.layoutQuantity.visibility = View.GONE
            holder.bind.layoutCart.visibility = View.VISIBLE
        }




//        if (menu.quantity == 0) {
//            holder.bind.btnCart.visibility = View.VISIBLE
//            holder.bind.layoutQuantity.visibility = View.GONE
//        } else {
//            holder.bind.layoutCart.visibility = View.GONE
//            holder.bind.layoutQuantity.visibility = View.VISIBLE
//        }
    }


    override fun getItemCount(): Int {
        return menuList.size
    }


    class MyViewHolder(val bind: MenuLayoutBinding) : RecyclerView.ViewHolder(bind.root)


}


