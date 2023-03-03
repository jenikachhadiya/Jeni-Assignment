package com.example.new_task.api.Get.modal


import com.google.gson.annotations.SerializedName


data class Product(
    @SerializedName("productId")
    val productId: Int?,
    @SerializedName("quantity")
    val quantity: Int?
)