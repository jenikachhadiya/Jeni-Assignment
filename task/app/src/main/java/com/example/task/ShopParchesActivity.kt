package com.example.task

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.new_task.entity.ShopList
import com.example.task.R
import com.example.task.databinding.ActivityShopParchesBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class ShopParchesActivity : AppCompatActivity() {
    lateinit var binding: ActivityShopParchesBinding
    private lateinit var recentViewAdaptor: RecentviewAdaptor
    private var recentList = mutableListOf<ShopList>()
    var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShopParchesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var getData = intent.getParcelableExtra<ShopList>("shop")

        if (getData != null) {
            binding.tvName.text = "${getData.shopName}"
            binding.tvDetails.text = getData.shopDetails
            binding.rateBar.rating = getData.rating
            binding.tvRate.text = getData.ratNum
            Glide.with(applicationContext).load(getData.img).centerCrop()
                .placeholder(R.drawable.ic_baseline_timer_24).into(binding.imageView)
        }

        shopNameData(recentList)

        recentViewAdaptor = RecentviewAdaptor(this, recentList)
        binding.recView.adapter = recentViewAdaptor
        binding.recView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        recentViewAdaptor = RecentviewAdaptor(this, recentList)
        binding.recView.adapter = recentViewAdaptor
        binding.recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recentViewAdaptor.addItemCounter(object : OnClickCount {
            override fun incrementDecrement(view: View, arrayList: ArrayList<ShopList>) {
                var itemcount = 0
                var itemPrice = 0.0;
                for (i in arrayList) {
                    itemcount += i.counter
                 itemPrice += i.price.times(i.counter).roundToLong()
                }
                if (snackbar != null) {
                    snackbar!!.view.findViewById<TextView>(R.id.itemCount).text =
                        getString(R.string.item).plus(itemcount.toString())
                    snackbar!!.view.findViewById<TextView>(R.id.tv_price).text =
                        itemPrice.toString()
                    if (itemcount!=0&&itemPrice!=0.0){
                        snackbar!!.show()
                    }else{
                        snackbar!!.dismiss()
                    }

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
                    snackbar!!.view.findViewById<TextView>(R.id.itemCount).text =
                        getString(R.string.item).plus("${shopList.counter}")
                    snackbar!!.view.findViewById<TextView>(R.id.tv_price).text =
                        "${shopList.price}"
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
        overridePendingTransition(R.anim.slide_left,R.anim.slide_bottomtotop)

    }



}

    fun shopNameData(shopList: MutableList<ShopList>) {
        shopList.add(
            ShopList(
                1,
                "https://img.freepik.com/free-vector/retro-furniture-logo_23-2148464123.jpg?w=740&t=st=1672036336~exp=1672036936~hmac=e6330984d1217f2d60451d83dbac8537d5556aea8b673c59464ba3496a07e4d8",
                "Luxurious Lumber",
                "furniture, household equipment or related materials and having a variety of different ...",
                2.5f,
                "BL-91, Plot No. 13/24",
                "2.5",
                123f
            )
        )
        shopList.add(
            ShopList(
                2,
                "https://img.freepik.com/free-vector/minimalist-furniture-logo-template-design_23-2148467616.jpg?w=740&t=st=1672036357~exp=1672036957~hmac=2fa016f51e352d283394ef2d63f313a999ca5c2d9728e00b30f19e50dab08725",
                "Luxury Leather Chairs",
                "furniture, household equipment or related materials and having a variety of different ...",
                3.5f,
                "BL-91, Plot No. 13/24",
                "3.5",
                234f
            )
        )
        shopList.add(
            ShopList(
                3,
                "https://img.freepik.com/free-vector/furniture-logo-concept_23-2148619623.jpg?w=740&t=st=1672036394~exp=1672036994~hmac=d71d26712779c0a2c0e382ba8d19b96082d0624b5a675e1736cad4ec2ef80970",
                "Meoble’s & Marbles",
                "furniture, household equipment or related materials and having a variety of different ...",
                4.5f,
                "BL-91, Plot No. 13/24",
                "4.5",
                452f
            )
        )
        shopList.add(
            ShopList(
                4,
                "https://img.freepik.com/free-vector/furniture-logo-with-minimalist-elements_23-2148628260.jpg?w=740&t=st=1672036453~exp=1672037053~hmac=658c4a7467134c3575910f06c8e8c97f91be03f9511360c94c9a8cf000f29adb",
                "Minimal Home",
                "furniture, household equipment or related materials and having a variety of different ...",
                1.5f,
                "BL-91, Plot No. 13/24",
                "1.5",
                123.5f
            )
        )
        shopList.add(
            ShopList(
                5,
                "https://img.freepik.com/free-vector/retro-furniture-logo_23-2148452344.jpg?w=740&t=st=1672036474~exp=1672037074~hmac=e40d30bebe5ec1538fae2077d8a359cb913cd33d7d479f56f0f2796dbea21253",
                "Oakley",
                "furniture, household equipment or related materials and having a variety of different ...",
                3.5f,
                "BL-91, Plot No. 13/24",
                "3.5",
                321.3f
            )
        )
        shopList.add(
            ShopList(
                6,
                "https://img.freepik.com/free-vector/elegant-furniture-logo-template_23-2148470895.jpg?w=740&t=st=1672036499~exp=1672037099~hmac=6f39f170d93736b042f21c8290c6a551daaf065b0f242df1005c472c7a61f000",
                "Odense Timber",
                "furniture, household equipment or related materials and having a variety of different ...",
                4.5f,
                "BL-91, Plot No. 13/24",
                "4.5",
                78.56f
            )
        )
        shopList.add(
            ShopList(
                7,
                "https://img.freepik.com/free-vector/minimalist-furniture-logo-template-with-illustration_23-2148619518.jpg?w=740&t=st=1672036527~exp=1672037127~hmac=7036b4c074fbe3e04d480fd83039a4f60b0d1faaaf076ee8b7795f5e7eb0af45",
                "Platinum Furniture",
                "furniture, household equipment or related materials and having a variety of different ...",
                3.5f,
                "BL-91, Plot No. 13/24",
                "3.5",
                123.4f
            )
        )
    }
