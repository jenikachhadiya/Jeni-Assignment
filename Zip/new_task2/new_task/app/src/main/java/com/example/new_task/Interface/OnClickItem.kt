package com.example.new_task.Interface

import com.example.new_task.entity.ShopList

interface OnClickItem {
    fun OnClickItemList(shopList: ShopList,pos:Int)
}