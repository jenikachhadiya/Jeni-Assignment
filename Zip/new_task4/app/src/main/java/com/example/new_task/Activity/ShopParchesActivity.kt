package com.example.new_task.Activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.new_task.Adaptor.RecentviewAdaptor
import com.example.new_task.Interface.OnClickCount
import com.example.new_task.ListUnity.ImgList
import com.example.new_task.R
import com.example.new_task.databinding.ActivityShopParchesBinding
import com.example.new_task.entity.ShopList
import com.google.android.material.snackbar.Snackbar


class ShopParchesActivity : AppCompatActivity() {
    lateinit var binding: ActivityShopParchesBinding
    private lateinit var recentViewAdaptor: RecentviewAdaptor
    private var recentList = mutableListOf<ShopList>()
    var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopParchesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //      var getImg = intent.getStringExtra("object")

        var getData = intent.getParcelableExtra<ShopList>("shop")

        if (getData != null) {
            binding.tvName.text = "${getData.shopName}"
            binding.tvDetails.text = getData.shopDetails
            binding.rateBar.rating = getData.rating
            binding.tvRate.text = getData.ratNum
            Glide.with(applicationContext).load(getData.img).centerCrop()
                .placeholder(R.drawable.ic_baseline_sync_24).into(binding.imageView)
        }
        var img = ImgList(this)
        img.shopNameData(recentList)

        recentViewAdaptor = RecentviewAdaptor(this, recentList)
        binding.recView.adapter = recentViewAdaptor
        binding.recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recentViewAdaptor.addItemCounter(object : OnClickCount {
            override fun incrementDecrement(view: View, arrayList: ArrayList<ShopList>) {
                var itemcount = 0
                var itemPrice = 0.0;
                for (i in arrayList) {
                    itemcount += i.counter
                    itemPrice += i.price.times(i.counter)

                }

                if (snackbar != null) {
                    snackbar!!.view.findViewById<TextView>(R.id.itemCount).text =
                        getString(R.string.item).plus(itemcount.toString())
                    snackbar!!.view.findViewById<TextView>(R.id.tv_price).text =
                        itemPrice.toFloat().toString()

                }else{
                    Toast.makeText(applicationContext, "0000", Toast.LENGTH_SHORT).show()
                }


            }

            override fun OnClickItem(view: View, shopList: ShopList, pos: Int) {

                if (snackbar == null) {
                    snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
                    val customSnackView: View =
                        layoutInflater.inflate(R.layout.snackbar_layout, null)
                    snackbar!!.view.setBackgroundColor(Color.TRANSPARENT)
                    val snackbarLayout = snackbar!!.view as Snackbar.SnackbarLayout
                    snackbarLayout.addView(customSnackView, 0)
                    snackbar!!.show()
                    Toast.makeText(applicationContext, "${shopList.shopName}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_right_left,R.anim.fade_left_right)

    }

}

