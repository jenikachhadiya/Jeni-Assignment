package com.example.new_task.Interface

import android.view.View
import com.example.new_task.entity.ShopList

interface OnClickCount {
    fun incrementDecrement(view: View, arrayList: ArrayList<ShopList>)
    fun OnClickItem(view: View,shopList: ShopList, pos:Int)

}