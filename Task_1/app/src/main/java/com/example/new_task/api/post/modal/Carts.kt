package com.example.new_task.api.post.modal


import com.google.gson.annotations.SerializedName



data class Carts(
    @SerializedName("date")
    val date: String?=null,
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("products")
    val products: List<Product?>?=null,
    @SerializedName("userId")
    val userId: String?=null
)
