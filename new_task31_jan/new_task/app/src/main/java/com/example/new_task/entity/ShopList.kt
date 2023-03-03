package com.example.new_task.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopList(
    var id: Int? = null,
    var img: String? = null,
    var shopName: String? = null,
    var shopDetails: String? = null,
    var rating: Float? = null,
    var address: String? = null,
    var ratNum: String? = null,
    var price: Float? = null,
    var counter: Int = 0,
    var viewType: Int = 0,
    var banner: String? = null,
    var Type: String? = null,
) : Parcelable

