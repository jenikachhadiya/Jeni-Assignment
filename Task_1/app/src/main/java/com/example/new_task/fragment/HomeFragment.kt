package com.example.new_task.fragment


import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.new_task.R
import com.example.new_task.activity.ShopParchesActivity
import com.example.new_task.adaptor.*

import com.example.new_task.api.Get.modal.shopListGet
import com.example.new_task.api.post.`interface`.ApiClint
import com.example.new_task.api.post.modal.AutoSliderGet
import com.example.new_task.api.post.modal.ResponseData
import com.example.new_task.clickInterface.ClickListner
import com.example.new_task.clickInterface.OnClick
import com.example.new_task.clickInterface.OnClickItem
import com.example.new_task.databinding.FragmentHomeFragmentBinding
import com.example.new_task.entity.AllFurniture
import com.example.new_task.entity.Category
import com.example.new_task.listUnity.ImgList
import com.example.new_task.preference.PrefClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class HomeFragment : Fragment() {

    //4 private late init var firebaseAnalytics: FirebaseAnalytics

    lateinit var binding: FragmentHomeFragmentBinding


    lateinit var autoSliderAdaptor: AutoSliderAdaptor
    private var imgList = mutableListOf<AutoSliderGet>()

    lateinit var categoriesAdaptor: CategoriesAdaptor
    private var categoryList = mutableListOf<Category>()

    lateinit var allFurnitureAdaptor: AllFurnitureAdaptor
    private var allFurnitureList = mutableListOf<shopListGet>()

    lateinit var shopListAdaptor: ShopListAdaptor
    private var shopList = mutableListOf<shopListGet?>()
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
           // Toast.makeText(requireContext(), "onAvailable", Toast.LENGTH_SHORT).show()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            binding.viewAnimatorScreen.displayedChild = 1
            //Toast.makeText(requireContext(), "On Changed", Toast.LENGTH_SHORT).show()

        }

        override fun onLost(network: Network) {
            super.onLost(network)
            binding.viewAnimatorScreen.displayedChild = 2
           // Toast.makeText(requireContext(), "OnLost", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // Toast.makeText(requireContext(), "on create", Toast.LENGTH_SHORT).show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeFragmentBinding.inflate(layoutInflater, container, false)

        val connectivityManager = getSystemService(
            requireContext(),
            ConnectivityManager::class.java
        ) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

        //addData
        imageData()

        //  binding.viewAnimatorScreen.displayedChild = 1

        //Auto Slider Img come On Api
        binding.viewAnimator.displayedChild = 0
        ApiClint.proClint(requireContext()).autoSlider()
            .enqueue(object : Callback<MutableList<AutoSliderGet>> {
                override fun onResponse(
                    call: Call<MutableList<AutoSliderGet>>,
                    response: Response<MutableList<AutoSliderGet>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            binding.viewAnimator.displayedChild = 1
                            val user = response.body()
                            var list = autoSliderAdaptor.setItem(user)
                            Log.e(TAG, "onResponse: ${response.body()}")
                        }
                    } else {
                        binding.viewAnimator.displayedChild = 0
                        //error  response
                        try {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseData>(){}.type
                            val responseData: ResponseData? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            if (responseData != null) {
                                Toast.makeText(requireContext(), responseData.msg, Toast.LENGTH_SHORT).show()
                            }

                        }catch (e: IOException){
                            e.printStackTrace()
                        }
                        Log.e(TAG, "onResponse: ${response.errorBody()}")
                    }

                }

                override fun onFailure(call: Call<MutableList<AutoSliderGet>>, t: Throwable) {
                    binding.viewAnimatorScreen.displayedChild = 2
                }

            })
        autoSliderAdaptor = AutoSliderAdaptor(requireActivity(), imgList)
        binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        binding.slider.setSliderAdapter(autoSliderAdaptor)
        binding.slider.scrollTimeInSec = 4
        binding.slider.isAutoCycle = true
        binding.slider.startAutoCycle()
        //binding.viewAnimator.displayedChild = 2


        //Category setup
        categoriesAdaptor = CategoriesAdaptor(requireActivity(), categoryList)
        binding.recView.adapter = categoriesAdaptor
        binding.recView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        //All Furniture Setup
        binding.viewAnimator3.displayedChild = 0
        ApiClint.proClint(requireContext()).jeweleryData()
            .enqueue(object : Callback<MutableList<shopListGet>> {
                override fun onResponse(
                    call: Call<MutableList<shopListGet>>,
                    response: Response<MutableList<shopListGet>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            binding.viewAnimator3.displayedChild = 1
                            val listData = response.body()
                            if (listData != null) {
                                allFurnitureAdaptor.setItem(listData)
                            }
                        }
                    } else {
                        //error  response
                        try {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseData>(){}.type
                            val responseData: ResponseData? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            if (responseData != null) {
                                Toast.makeText(requireContext(), responseData.msg, Toast.LENGTH_SHORT).show()
                            }

                        }catch (e:IOException){
                            e.printStackTrace()
                        }
                    }

                }
                override fun onFailure(call: Call<MutableList<shopListGet>>, t: Throwable) {
                    binding.viewAnimatorScreen.displayedChild = 2
                    Toast.makeText(requireContext(), "APi Fail", Toast.LENGTH_SHORT).show()
                }

            })
        allFurnitureAdaptor = AllFurnitureAdaptor(requireActivity(), allFurnitureList)
        binding.recView2.adapter = allFurnitureAdaptor
        binding.recView2.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        val getDataFromDataBase = PrefClass(requireContext()).getUserData()
        Log.e(TAG, "onCreateView: $getDataFromDataBase")
        Log.e(TAG, "onCreateView: ${getDataFromDataBase?.email}")

        //products List
        binding.recViewAnimator.displayedChild = 0
        ApiClint.proClint(requireContext()).shopListData()
            .enqueue(object : Callback<ArrayList<shopListGet?>> {
                override fun onResponse(
                    call: Call<ArrayList<shopListGet?>>,
                    response: Response<ArrayList<shopListGet?>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            binding.recViewAnimator.displayedChild = 1
                            val listData = response.body()
                            if (listData != null) {
                                shopListAdaptor.setItem(listData)
                            }
                        }
                    } else {
                        //error  response
                        try {
                            val gson = Gson()
                            val type = object : TypeToken<ResponseData>(){}.type
                            val responseData: ResponseData? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            if (responseData != null) {
                                Toast.makeText(requireContext(), responseData.msg, Toast.LENGTH_SHORT).show()
                            }

                        }catch (e:IOException){
                            e.printStackTrace()
                        }
                    }

                }

                override fun onFailure(call: Call<ArrayList<shopListGet?>>, t: Throwable) {
                    binding.viewAnimatorScreen.displayedChild = 2
                    Toast.makeText(requireContext(), "APi Fail", Toast.LENGTH_SHORT).show()
                }

            })
        shopListAdaptor = ShopListAdaptor(requireActivity(), shopList)
        binding.recView3.adapter = shopListAdaptor
        binding.recView3.layoutManager = LinearLayoutManager(requireActivity())
        /* val manger = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
         manger.spanSizeLookup = object : SpanSizeLookup() {
             override fun getSpanSize(position: Int): Int {
                 return when(list[position]?.viewType){
                              1 -> 2
                             else -> 1
                 }
             }
         }
         binding.recView3.layoutManager = manger
 */


        //Click Events
        categoriesAdaptor.onClickListener(object : OnClick {
            override fun onclicklistner(category: Category) {

            }

        })
        allFurnitureAdaptor.onClickEvent(object : ClickListner {
            override fun onClickList(allFurniture: AllFurniture) {
                Toast.makeText(requireActivity(), "${allFurniture.Tital}", Toast.LENGTH_SHORT)
                    .show()
            }

        })
        shopListAdaptor.onClickEvent(object : OnClickItem {
            override fun OnClickItemList(shopListId: Int, pos: Int) {
                val intent = Intent(requireContext(), ShopParchesActivity::class.java)
                intent.putExtra("shop", shopListId)
                startActivity(intent)
                requireActivity().overridePendingTransition(
                    R.anim.fade_enter_from_left,
                    R.anim.fade_enter_from_right
                )
            }

        })
        return binding.root

    }

    private fun imageData() {
        var img = ImgList(requireActivity())
        //  img.imageData(imgList)
        img.categoryData(categoryList)
        //img.allFurnitureData(allFurnitureList)
        // img.shopNameData(shopList)

    }
}


