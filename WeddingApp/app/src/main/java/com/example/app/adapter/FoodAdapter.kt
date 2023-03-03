package com.example.app.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.app.listner.OnListitemClickListener
import com.example.app.model.Food
import com.example.weddingapp.databinding.FoodLayoutBinding


class FoodAdapter(var context: Context, var foodList: MutableList<Food>) :
    RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {

    lateinit var binding: FoodLayoutBinding


    private lateinit var listener: OnListitemClickListener

    fun setOnlistItemClickListener(listener: OnListitemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = FoodLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var food = foodList[position]

        holder.bind.tvTitle.text = food.title
        holder.bind.tvDesc.text = food.desc
        holder.bind.tvLocation.text = food.address
        holder.bind.tvRating.rating = food.ratting.toString().toFloat()
        holder.bind.tvPoint.text = "${food.rattingtext}"



        binding.ivThumbnail.load(foodList[position].image) {
            crossfade(true)

        }

        holder.bind.cardParent.setOnClickListener {
            listener.onCardClicked(position, food)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class MyViewHolder(itemView: FoodLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }


}