package com.gautam.validatonformgrewon

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.adapter.ListRecyclerAdepter
import com.gautam.validatonformgrewon.apimodal.DetailsResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.databinding.ActivityDetailBinding
import com.gautam.validatonformgrewon.databinding.ToastRecyclerBinding
import com.gautam.validatonformgrewon.modal.ListRecyclerBurgurKing
import com.gautam.validatonformgrewon.utilslist.UtilList
import com.google.android.material.snackbar.Snackbar
import com.kishandonga.csbx.CustomSnackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var adepter: ListRecyclerAdepter
    lateinit var notification: MyFaireBase

    //   var list= UtilList.getMenuList(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiCalling()

        var messagess = intent.getStringExtra("title")
        var description = intent.getStringExtra("messagebody")
        if (messagess != null) {
            Toast.makeText(this, messagess, Toast.LENGTH_SHORT).show()
        }
        //   val dataList = UtilList.getMenuList(this)


        val list = UtilList.getMenuList(this)
        2000 - 12 - 31

        adepter = ListRecyclerAdepter(this, list)
        binding.rvMenurecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMenurecycler.adapter = adepter
        /*  var grid = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

          grid.setSpanSizeLookup(object : SpanSizeLookup() {
              override fun getSpanSize(position: Int): Int {
                  return when (adepter.getItemViewType(position)) {
                     2-> 1
                      else -> 2
                  }
              }
          })
           binding.rvMenurecycler.layoutManager =grid
  */




        adepter.addAdition(object : ListRecyclerAdepter.TotalAmount {
            override fun addamount(arraylist: ArrayList<ListRecyclerBurgurKing>, view: View) {
                var itemCount = 0
                var totalAmount = 0.0
                for (data in arraylist) {
                    itemCount += data.quantity
                    totalAmount += data.price.toDouble().times(data.quantity)
                }

                val layout = ToastRecyclerBinding.inflate(layoutInflater)
                layout.tvTotal.text = totalAmount.toString()
                layout.tvItem.text = itemCount.toString()
                CustomSnackbar(this@DetailActivity).show {
                    customView(layout.root)
                    duration(Snackbar.LENGTH_INDEFINITE)
                }
            }

            override fun removeamount(
                arraylist: ArrayList<ListRecyclerBurgurKing>, view: View
            ) {
                var itemCount = 0
                var totalAmount = 0.0
                for (data in arraylist) {
                    itemCount += data.quantity
                    totalAmount += data.price.toDouble().times(data.quantity)
                }
                val layout = ToastRecyclerBinding.inflate(layoutInflater)
                layout.tvTotal.text = totalAmount.toString()
                layout.tvItem.text = itemCount.toString()
                CustomSnackbar(this@DetailActivity).show {
                    customView(layout.root)
                    duration(Snackbar.LENGTH_SHORT)
                }
            }
        })
    }

    private fun apiCalling() {

        var call = ApiClient.listView(this).getCatreUp()
        call.enqueue(object : Callback<DetailsResponse> {
            override fun onResponse(
                call: Call<DetailsResponse>,
                response: Response<DetailsResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        var data = response.body()
                        binding.tvBurgurking.text = data?.title
                        binding.tvDescritons.text=data?.description
                        binding.RtRattingbar.rating= data?.rating?.rate?.toFloat()!!
                        binding.rattingPoint.text=data.rating.rate.toString()
                        Glide.with(this@DetailActivity)
                            .load(data.image)
                            .placeholder(R.drawable.ic_baseline_hourglass_top_24)
                            .error(R.drawable.ic_no_image)
                            .into(binding.ivBthumbnail)
                    }
                }
            }

            override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })


    }
}
