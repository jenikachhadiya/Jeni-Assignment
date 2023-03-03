package com.gautam.validatonformgrewon.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gautam.validatonformgrewon.DetailActivity
import com.gautam.validatonformgrewon.MessageActivity
import com.gautam.validatonformgrewon.adapter.AutoAdepter
import com.gautam.validatonformgrewon.adapter.ItemAdapter
import com.gautam.validatonformgrewon.adapter.ListViewAdepter
import com.gautam.validatonformgrewon.adapter.ShoppingAdepter
import com.gautam.validatonformgrewon.apimodal.AutoViewResponse
import com.gautam.validatonformgrewon.apimodal.CategoriesResponse
import com.gautam.validatonformgrewon.apimodal.HomeResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.databinding.FragmentHomeBinding
import com.gautam.validatonformgrewon.utilslist.UtilList.Companion.getshoppingList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment(), ListViewAdepter.OnClickEvent {

    lateinit var binding: FragmentHomeBinding
    lateinit var autoadepter: AutoAdepter
    lateinit var itemadepter: ItemAdapter
    lateinit var shoping: ShoppingAdepter

    //lateinit var listview: ListViewAdepter
    lateinit var listViewAdepter: ListViewAdepter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoslieder()
        listViewApi()
        categoriyApi()


        binding.imMessage.setOnClickListener {
            var intent = Intent(requireContext(), MessageActivity::class.java)
            startActivity(intent)
        }


        shoping = ShoppingAdepter(requireContext(), getshoppingList(requireContext()))
        binding.revShop.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.revShop.adapter = shoping


    }

    private fun categoriyApi() {


          binding.vcCatergoriy.displayedChild=0
        itemadepter = ItemAdapter(requireContext(), arrayListOf())
        binding.rcvItem.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvItem.adapter = itemadepter


        var cate = ApiClient.listView(requireContext()).getCategories()
        cate.enqueue(object : Callback<ArrayList<CategoriesResponse>> {
            override fun onResponse(
                call: Call<ArrayList<CategoriesResponse>>,
                response: Response<ArrayList<CategoriesResponse>>
            ) {
                Log.e("TAG", "categorisr: " + response)
                Log.e("TAG", "categorisr bodiy: " + response.body())
                Log.e("TAG", "categorisr errorbodiy: " + response.errorBody())

                if (response.isSuccessful) {
                    binding.vcCatergoriy.displayedChild=1
                   // Toast.makeText(requireContext(), "categories successful", Toast.LENGTH_SHORT)
                   //     .show()
                    Log.e("TAG", "categorisr errorbodiy: " + response.errorBody())

                    val category = response.body()
                    if (category != null) {
                        itemadepter.setItems(category)
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<CategoriesResponse>>, t: Throwable) {
                t.printStackTrace()
                Log.e("TAG", "onFailure categoriy: " + t.cause)
                binding.vcCatergoriy.displayedChild=2
            }

        })

    }

    private fun listViewApi() {
        binding.vtViewanimator.displayedChild = 0
        listViewAdepter = ListViewAdepter(requireContext(), arrayListOf())
        binding.revList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.revList.adapter = listViewAdepter
        listViewAdepter.setOnlistItemClickListener(this)

        var list = ApiClient.listView(requireContext()).getListView()
        list.enqueue(object : Callback<ArrayList<HomeResponse>> {
            override fun onResponse(
                call: Call<ArrayList<HomeResponse>>,
                response: Response<ArrayList<HomeResponse>>
            ) {

                if (response.isSuccessful) {
                    binding.vtViewanimator.displayedChild = 0
                    Toast.makeText(requireContext(), "successful list ", Toast.LENGTH_SHORT).show()

                    var allList = response.body()

                    if (allList != null) {
                        binding.vtViewanimator.displayedChild = 1
                        listViewAdepter.setItems(allList)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<HomeResponse>>, t: Throwable) {
                //      Toast.makeText(requireContext(), "List is fail", Toast.LENGTH_SHORT).show()
                binding.vtViewanimator.displayedChild = 2
            }

        })
    }

    private fun autoslieder() {

        binding.vtAutoslider.displayedChild = 0
        autoadepter = AutoAdepter(requireContext())
        binding.ivViewAutoscroll.adapter = autoadepter
        binding.dotsIndicator.attachTo(binding.ivViewAutoscroll)

        var image = ApiClient.listView(requireContext()).getAutoViewImage()
        image.enqueue(object : Callback<ArrayList<AutoViewResponse>> {
            override fun onResponse(
                call: Call<ArrayList<AutoViewResponse>>,
                response: Response<ArrayList<AutoViewResponse>>
            ) {


                if (response.isSuccessful) {
//                    Toast.makeText(requireContext(), "Viewpager successful", Toast.LENGTH_SHORT)
//                        .show()
                    binding.vtAutoslider.displayedChild = 1

                    val list = response.body()

                    //    list?.let {
                    if (list != null) {
                        autoadepter.setItems(list)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<AutoViewResponse>>, t: Throwable) {
                // Toast.makeText(requireContext(), "faile image", Toast.LENGTH_SHORT).show()
                binding.vtAutoslider.displayedChild = 2

            }

        })


        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            if (binding.ivViewAutoscroll.currentItem == autoadepter.count - 1) {
                binding.ivViewAutoscroll.currentItem = 0
            } else {
                binding.ivViewAutoscroll.currentItem++
            }
        }

        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            },
            1000,
            2000
        )
    }

    override fun onClickEvent(id: Int, position: Int) {
        var intent = Intent(requireContext(), DetailActivity::class.java)
        ActivityOptions.makeSceneTransitionAnimation(requireContext() as Activity?)
        //    intent.putExtra("Food", food)
        startActivity(intent)
    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 100) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            }
//        }
//    }
}
