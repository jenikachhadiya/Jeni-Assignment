package com.example.new_task.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.new_task.Adaptor.AutoSliderAdaptor
import com.example.new_task.Adaptor.CategoriesAdaptor
import com.example.new_task.ListUnity.ImgList
import com.example.new_task.R
import com.example.new_task.databinding.FragmentHomeFragmentBinding
import com.example.new_task.entity.Category
import com.example.new_task.entity.ImageSlider
import com.smarteist.autoimageslider.SliderView


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeFragmentBinding

    lateinit var autoSliderAdaptor: AutoSliderAdaptor
    private var imgList = mutableListOf<ImageSlider>()

    lateinit var categoriesAdaptor: CategoriesAdaptor
    private var categoryList = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeFragmentBinding.inflate(layoutInflater, container, false)

        //addData
        imageData()


        autoSliderAdaptor = AutoSliderAdaptor(requireActivity(), imgList)

        binding.slider.autoCycleDirection  = SliderView.LAYOUT_DIRECTION_LTR
        binding.slider.setSliderAdapter(autoSliderAdaptor)
        binding.slider.scrollTimeInSec = 3
        binding.slider.isAutoCycle = true
        binding.slider.startAutoCycle()

        //Category setup
        categoriesAdaptor = CategoriesAdaptor(requireActivity(),categoryList)
        binding.recView.adapter = categoriesAdaptor
        binding.recView.layoutManager = LinearLayoutManager(requireActivity())






        return binding.root

    }

    private fun imageData() {
        var img = ImgList(requireActivity())
        img.imageData(imgList)
        img.categoryData(categoryList)
    }

}