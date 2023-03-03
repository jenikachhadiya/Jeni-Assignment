package com.example.new_task.api.post.modal

import com.google.gson.annotations.SerializedName


data class Product(
    @SerializedName("productId")
    val productId: Int?=null,
    @SerializedName("quantity")
    var quantity: Int?=null
)
