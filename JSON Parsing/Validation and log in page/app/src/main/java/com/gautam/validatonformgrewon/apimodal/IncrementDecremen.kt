package com.gautam.validatonformgrewon.apimodal


import com.google.gson.annotations.SerializedName


data class IncrementDecremen(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("products")
    val products: String,
    @SerializedName("userId")
    val userId: Int
)