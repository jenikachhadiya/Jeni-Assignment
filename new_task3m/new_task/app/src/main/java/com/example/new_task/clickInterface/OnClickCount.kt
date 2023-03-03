package com.example.new_task.clickInterface

import android.view.View
import com.example.new_task.api.Get.modal.shopListGet
import com.example.new_task.entity.ShopList

interface OnClickCount {
   /* fun incrementDecrement(view: View, arrayList: ArrayList<ShopList?>)
    fun OnClickItem(view: View,shopList: ShopList, pos:Int)*/
   fun incrementDecrement(view: View, arrayList: ArrayList<shopListGet?>)
   fun OnClickItem(view: View,shopList: shopListGet, pos:Int)

}