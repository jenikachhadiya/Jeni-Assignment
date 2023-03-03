package com.example.new_task.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopList(
    var id: Int,
    var img: String?,
    var shopName: String?,
    var shopDetails: String?,
    var rating: Float?,
    var address: String?,
    var ratNum: String?,
    var price: Float?,
    var counter: Int = 0,
    var viewType:Int = 0
) : Parcelable

