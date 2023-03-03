package com.example.app.activitys

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.app.adapter.MenuAdapter
import com.example.app.model.Food
import com.example.app.model.Menu
import com.example.weddingapp.R
import com.example.weddingapp.databinding.ActivityRestaurantBinding
import com.example.weddingapp.databinding.CustomSnackebarLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.kishandonga.csbx.CustomSnackbar


    class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding

    var menuList = arrayListOf<Menu>()
    lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var food: Food = intent.getParcelableExtra<Food>("Food")!!
        Log.e("Data", "onCreate: " + food.toString())

        binding.tvTitle.text = "${food.title}"
        binding.tvDesc.text = "${food.desc}"
        binding.tvRating.rating = food.ratting.toString().toFloat()
        binding.tvPoint.text = "${food.rattingtext}"
        binding.ivBullet.setImageResource(R.drawable.ic_bullet)
        binding.tvLocation.text = "${food.address}"

        binding.ivThumbnail.load(food.image) {
            crossfade(true)
        }


        preparaData()

        menuAdapter = MenuAdapter(this, menuList)
        binding.rcvMenu.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvMenu.adapter = menuAdapter


        menuAdapter.addCommunicator(object : MenuAdapter.Communicator {
            override fun addAmount(arrayList: ArrayList<Menu>) {
                var itemCount = 0
                var totalAmount = 0.0
                for (data in arrayList) {
                    totalAmount += data.price.times(data.quantity)
                    itemCount += data.quantity

                }

                val layout = CustomSnackebarLayoutBinding.inflate(layoutInflater)
                layout.tvTotalText.text = totalAmount.toString()
                layout.tvItem.text = itemCount.toString()
                CustomSnackbar(this@RestaurantActivity).show {
                    customView(layout.root)
                    duration(Snackbar.LENGTH_INDEFINITE)
                }
            }

            override fun removeAmount(arrayList: ArrayList<Menu>) {

                var itemCount = 0
                var totalAmount = 0.0
                for (data in arrayList) {
                    totalAmount += data.price.times(data.quantity)
                    itemCount += data.quantity

                }
                val layout = CustomSnackebarLayoutBinding.inflate(layoutInflater)
                layout.tvTotalText.text = totalAmount.toString()
                layout.tvItem.text = itemCount.toString()
                CustomSnackbar(this@RestaurantActivity).show {
                    customView(layout.root)
                    duration(Snackbar.LENGTH_LONG)
                }
            }
        })
    }

    private fun preparaData() {

        menuList.add(Menu(1, "kaju paneer sabji", 420.5))
        menuList.add(Menu(1, "kaju paneer sabji", 420.5))
        menuList.add(Menu(1, "kaju paneer sabji", 420.5))
        menuList.add(Menu(1, "kaju paneer sabji", 420.5))
        menuList.add(Menu(1, "kaju paneer sabji", 420.5))
        menuList.add(Menu(1, "kaju paneer sabji", 420.5))

    }


}