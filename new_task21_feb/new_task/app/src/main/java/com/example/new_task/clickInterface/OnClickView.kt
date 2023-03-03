package com.example.new_task.clickInterface

import android.view.View
import com.example.new_task.entity.ShopList

interface OnClickView {

    fun OnClickItem(view: View,shopList: ShopList, pos:Int)

}