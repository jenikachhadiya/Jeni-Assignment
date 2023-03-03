package com.example.new_task.clickInterface

import com.example.new_task.entity.ShopList

interface OnClickItem {
    fun OnClickItemList(shopList: ShopList,pos:Int)
}