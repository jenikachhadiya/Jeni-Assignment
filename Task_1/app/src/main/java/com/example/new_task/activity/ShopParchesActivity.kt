package com.example.new_task.activity

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ActionMode
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.new_task.adaptor.RecentviewAdaptor
import com.example.new_task.clickInterface.OnClickCount
import com.example.new_task.listUnity.ImgList
import com.example.new_task.R
import com.example.new_task.api.Get.modal.proGet
import com.example.new_task.api.Get.modal.shopListGet
import com.example.new_task.api.Get.modal.shopPurches
import com.example.new_task.api.post.`interface`.ApiClint
import com.example.new_task.api.post.modal.Carts
import com.example.new_task.api.post.modal.Product
import com.example.new_task.api.post.modal.ResponseData
import com.example.new_task.databinding.ActivityShopParchesBinding
import com.example.new_task.entity.ShopList
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.roundToLong


class ShopParchesActivity : AppCompatActivity() {
    lateinit var binding: ActivityShopParchesBinding
    private lateinit var recentViewAdaptor: RecentviewAdaptor
    private var recentList = arrayListOf<shopListGet?>()
    private var recentItemList = mutableListOf<ShopList>()
    var snackbar: Snackbar? = null
    var proId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopParchesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  var getImg = intent.getStringExtra("object")

        val getTitle = intent.getStringExtra("Tit")
        val getMess = intent.getStringExtra("Mes")
        var id = intent.getIntExtra("shop", 0)

        getProduct()
        shopListData()


        val img = ImgList(this)
        img.shopNameData(recentItemList)

        recentViewAdaptor = RecentviewAdaptor(this@ShopParchesActivity, recentList)
        binding.recView.adapter = recentViewAdaptor
        binding.recView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //cart item add remove
        recentViewAdaptor.addItemCounter(object : OnClickCount {
            override fun incrementDecrement(
                view: View,
                arrayList: ArrayList<shopListGet?>
            ) {
                //increment
                var itemcount = 0
                var itemPrice = 0.0;
                var list: List<Product>
                for (i in arrayList) {
                    itemcount += i?.counter!!
                    itemPrice += i.price?.times(i.counter)?.roundToLong()!!
                    proId = i.id!!
                    list = listOf(
                        Product(
                            productId = proId,
                            quantity = itemcount
                        )
                    )
                    Log.e(TAG, "incrementDecrement: $i")
                    //send Data
                    ApiClint.proClint(this@ShopParchesActivity)
                        .cartsData(carts = Carts(products = list))
                        .enqueue(object : Callback<Carts> {
                            override fun onResponse(
                                call: Call<Carts>,
                                response: Response<Carts>
                            ) {
                                if (response.isSuccessful) {
                                    if (response.body() != null) {
                                        for (i in response.body()!!.products!!) {
                                            val data = i?.quantity
                                            Log.e(TAG, "onResponse: $data")

                                            if (i != null) {
                                                snackbar!!.view.findViewById<TextView>(
                                                    R.id.itemCount
                                                ).text =
                                                    getString(R.string.item).plus(
                                                        itemcount.toString()
                                                    )
                                            }
                                            snackbar!!.view.findViewById<TextView>(
                                                R.id.tv_price
                                            ).text =
                                                itemPrice.toString()

                                            if (snackbar != null) {
                                                if (i != null) {
                                                    if (i.quantity != 0) {
                                                        snackbar!!.show()
                                                    } else {
                                                        snackbar!!.dismiss()
                                                    }
                                                }
                                            }
                                        }
                                        Log.e(TAG, "onResponse: $itemcount")
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<Carts>,
                                t: Throwable
                            ) {

                            }
                        })

                }
            }

            override fun OnClickItem(
                view: View,
                shopList: shopListGet,
                pos: Int
            ) {
                snackBarShow(view, shopList, pos)
            }
        })

    }

    private fun snackBarShow(view: View, shopList: shopListGet, pos: Int) {
        if (snackbar == null) {
            snackbar = Snackbar.make(
                view,
                "",
                Snackbar.LENGTH_INDEFINITE
            )
            val customSnackView: View =
                layoutInflater.inflate(
                    R.layout.snackbar_layout,
                    null
                )
            snackbar!!.view.setBackgroundColor(Color.TRANSPARENT)
            val snackbarLayout =
                snackbar!!.view as Snackbar.SnackbarLayout
            snackbarLayout.addView(customSnackView, 0)
            snackbar!!.view.findViewById<TextView>(R.id.itemCount).text =
                getString(R.string.item).plus("")
            snackbar!!.view.findViewById<TextView>(R.id.tv_price).text =
                ""
            snackbar!!.show()
        }
    }

    private fun getQuantity() {
        ApiClint.proClint(this@ShopParchesActivity).cartGet()
            .enqueue(object : Callback<MutableList<proGet>> {
                override fun onResponse(
                    call: Call<MutableList<proGet>>,
                    response: Response<MutableList<proGet>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val data = response.body()!![0].products
                            recentViewAdaptor.setCounter(data)
/*//                            recentViewAdaptor.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
//                                override fun onChanged() {
//                                    val listData = recentViewAdaptor.updateList()
//                                    showSnackBar(listData)
//                                    super.onChanged()
//                                }
//                            })
    */
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            val listData = recentViewAdaptor.updateList()
                            showSnackBar(listData)
                        }, 300)
                    } else {
                        //error  response
                        try {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseData>() {}.type
                            val responseData: ResponseData? =
                                gson.fromJson(response.errorBody()!!.charStream(), type)
                            if (responseData != null) {
                                Toast.makeText(
                                    applicationContext,
                                    responseData.msg,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<proGet>>,
                    t: Throwable
                ) {

                }
            })
    }

    private fun showSnackBar(listData: ArrayList<shopListGet?>) {
        try {
            var counter = 0
            var price = 0.0
            if (snackbar == null) {
                snackbar = Snackbar.make(
                    binding.root,
                    "",
                    Snackbar.LENGTH_INDEFINITE
                )
                val customSnackView: View =
                    layoutInflater.inflate(
                        R.layout.snackbar_layout,
                        null
                    )
                snackbar!!.view.setBackgroundColor(Color.TRANSPARENT)
                val snackbarLayout =
                    snackbar!!.view as Snackbar.SnackbarLayout
                snackbarLayout.addView(customSnackView, 0)
                if (listData.isNotEmpty()) {
                    for (i in listData) {
                        if (i!!.counter != 0) {
                            counter += i.counter
                            price += i.price?.times(i.counter)?.roundToLong()!!
                        } else {
                            snackbar!!.dismiss()
                        }
                    }
                }
                snackbar!!.view.findViewById<TextView>(R.id.itemCount).text =
                    getString(R.string.item).plus(counter)
                snackbar!!.view.findViewById<TextView>(R.id.tv_price).text =
                    price.toString()
                snackbar!!.show()
            }


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun shopListData() {
        binding.viewAnimator4.displayedChild = 0
        ApiClint.proClint(this).shopListData()
            .enqueue(object : Callback<ArrayList<shopListGet?>> {
                override fun onResponse(
                    call: Call<ArrayList<shopListGet?>>,
                    response: Response<ArrayList<shopListGet?>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            binding.viewAnimator4.displayedChild = 1
                            val list = response.body()
                            if (list != null) {
                                recentViewAdaptor.setItem(list)
                                getQuantity()
                            }
                        }
                    } else {
                        //error  response
                        try {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseData>() {}.type
                            val responseData: ResponseData? =
                                gson.fromJson(response.errorBody()!!.charStream(), type)
                            if (responseData != null) {
                                Toast.makeText(
                                    applicationContext,
                                    responseData.msg,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<shopListGet?>>, t: Throwable) {
                    binding.viewAnimator4.displayedChild = 2
                }
            })
    }

    private fun getProduct() {
        binding.viewAnimator.displayedChild = 0
        ApiClint.proClint(this).shopPurData().enqueue(object : Callback<shopPurches> {
            override fun onResponse(
                call: Call<shopPurches>,
                response: Response<shopPurches>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        binding.viewAnimator.displayedChild = 1
                        var getData = response.body()
                        if (getData != null) {
                            binding.tvName.text = getData.title
                            binding.tvDetails.text = getData.description
                            binding.rateBar.rating = getData.rating.rate.toFloat()
                            binding.tvRate.text = getData.rating.rate.toString()
                            Glide.with(applicationContext).load(getData.image)
                                .placeholder(R.drawable.wait).into(binding.imageView)
                        }
                    }
                } else {
                    binding.viewAnimator.displayedChild = 2
                    //error  response
                    try {
                        val gson = Gson()
                        val type = object : TypeToken<ResponseData>() {}.type
                        val responseData: ResponseData? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        if (responseData != null) {
                            Toast.makeText(applicationContext, responseData.msg, Toast.LENGTH_SHORT)
                                .show()
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<shopPurches>, t: Throwable) {
                binding.viewAnimator.displayedChild = 2
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)

    }


}

