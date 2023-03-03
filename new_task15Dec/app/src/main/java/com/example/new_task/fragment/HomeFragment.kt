package com.example.new_task.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.new_task.activity.ShopParchesActivity
import com.example.new_task.adaptor.AllFurnitureAdaptor
import com.example.new_task.adaptor.AutoSliderAdaptor
import com.example.new_task.adaptor.CategoriesAdaptor
import com.example.new_task.adaptor.ShopListAdaptor
import com.example.new_task.clickInterface.ClickListner
import com.example.new_task.clickInterface.OnClick
import com.example.new_task.clickInterface.OnClickItem
import com.example.new_task.listUnity.ImgList
import com.example.new_task.R
import com.example.new_task.databinding.FragmentHomeFragmentBinding
import com.example.new_task.entity.AllFurniture
import com.example.new_task.entity.Category
import com.example.new_task.entity.ImageSlider
import com.example.new_task.entity.ShopList
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.smarteist.autoimageslider.SliderView


class HomeFragment : Fragment() {

   //4 private lateinit var firebaseAnalytics: FirebaseAnalytics

    lateinit var binding: FragmentHomeFragmentBinding


    lateinit var autoSliderAdaptor: AutoSliderAdaptor
    private var imgList = mutableListOf<ImageSlider>()

    lateinit var categoriesAdaptor: CategoriesAdaptor
    private var categoryList = mutableListOf<Category>()

    lateinit var allFurnitureAdaptor: AllFurnitureAdaptor
    private var allFurnitureList = mutableListOf<AllFurniture>()

    lateinit var shopListAdaptor: ShopListAdaptor
    private var shopList = mutableListOf<ShopList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // firebaseAnalytics = Firebase.analytics

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeFragmentBinding.inflate(layoutInflater, container, false)
        //addData
        imageData()


        autoSliderAdaptor = AutoSliderAdaptor(requireActivity(), imgList)

        binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        binding.slider.setSliderAdapter(autoSliderAdaptor)
        binding.slider.scrollTimeInSec = 4
        binding.slider.isAutoCycle = true
        binding.slider.startAutoCycle()

        //Category setup
        categoriesAdaptor = CategoriesAdaptor(requireActivity(), categoryList)
        binding.recView.adapter = categoriesAdaptor
        binding.recView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)


        //All Furniture Setup
        allFurnitureAdaptor = AllFurnitureAdaptor(requireActivity(), allFurnitureList)
        binding.recView2.adapter = allFurnitureAdaptor
        binding.recView2.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        //ShopList
        shopListAdaptor = ShopListAdaptor(requireActivity(), shopList)
        binding.recView3.adapter = shopListAdaptor
        binding.recView3.layoutManager = LinearLayoutManager(requireActivity())


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
            override fun OnClickItemList(shopList: ShopList, pos: Int) {

                var intent = Intent(requireContext(), ShopParchesActivity::class.java)
                intent.putExtra("shop", shopList)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.fade_enter_from_left, R.anim.fade_enter_from_right)
                // Transition.(R.anim.fade_in,R.anim.fade_out)




            }

        })


        return binding.root

    }

    private fun imageData() {
        var img = ImgList(requireActivity())
        img.imageData(imgList)
        img.categoryData(categoryList)
        img.allFurnitureData(allFurnitureList)
        img.shopNameData(shopList)


    }




}


