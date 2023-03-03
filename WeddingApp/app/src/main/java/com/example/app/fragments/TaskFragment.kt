package com.example.app.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.app.activitys.RestaurantActivity
import com.example.app.adapter.AutoImageAdapter
import com.example.app.adapter.ElectronicAdapter
import com.example.app.adapter.FoodAdapter
import com.example.app.adapter.ItemAdapter
import com.example.app.listner.OnListitemClickListener
import com.example.app.model.AutoSlider
import com.example.app.model.Food
import com.example.app.utils.Utils.Companion.getCategoryData
import com.example.app.utils.Utils.Companion.getElectronicData
import com.example.app.utils.Utils.Companion.getfoodData
import com.example.weddingapp.R
import com.example.weddingapp.databinding.FragmentTaskBinding
import java.util.*


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding


    var itemList = mutableListOf<AutoSlider>()
    lateinit var adapter: AutoImageAdapter


    lateinit var itemAdapter: ItemAdapter


    lateinit var electronicAdapter: ElectronicAdapter


    lateinit var foodAdapter: FoodAdapter


    private var currentPage = 0
    private var num_pages = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)



        serchview()

        Autoslider()


        // item list

        itemAdapter = ItemAdapter(requireContext(), getCategoryData(requireContext()))
        binding.rcvItem.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvItem.adapter = itemAdapter


        // electronic list


        electronicAdapter = ElectronicAdapter(requireContext(), getElectronicData(requireContext()))
        binding.rcvElectronics.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvElectronics.adapter = electronicAdapter


        // food list


        foodAdapter = FoodAdapter(requireContext(), getfoodData(requireContext()))
        binding.rcvFood.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvFood.adapter = foodAdapter


        foodAdapter.setOnlistItemClickListener(object : OnListitemClickListener {
            override fun onCardClicked(pos: Int, food: Food) {
                var intent = Intent(requireContext(), RestaurantActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(requireActivity())
                intent.putExtra("Food", food)
                startActivity(intent, options.toBundle())
            }

        })

        return binding.root
    }


    private fun serchview() {


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


            }

            override fun afterTextChanged(s: Editable) {

                //  filter(s.toString())
            }
        })

    }

    private fun Autoslider() {


        itemList.add(AutoSlider(1, getString(R.string._Sale_Banner_images)))
        itemList.add(AutoSlider(1, getString(R.string._Shoes_images)))
        itemList.add(AutoSlider(1, getString(R.string._Watch_images)))
        itemList.add(AutoSlider(1, getString(R.string._Beatuy_Natural)))


        adapter = AutoImageAdapter(requireContext(), imageList = itemList)
        binding.ivViewAutoscroll.adapter = adapter

        binding.dotsIndicator.attachTo(binding.ivViewAutoscroll)

        //var density=resources.displayMetrics.density

        num_pages = itemList.size


        var handler = Handler()
        var update = Runnable {
            if (currentPage == num_pages) {
                currentPage = 0


                binding.ivViewAutoscroll.addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        currentPage = position
                    }

                    override fun onPageScrollStateChanged(state: Int) {}
                })

            }
            binding.ivViewAutoscroll.setCurrentItem(currentPage++, true)
        }


        var swimTimer = Timer()
        swimTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }

        }, 1000, 2000)
    }


}