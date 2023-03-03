package com.example.new_task.api.Get.modal


import com.google.gson.annotations.SerializedName


data class proGet(
    @SerializedName("date")
    val date: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("products")
    val products: ArrayList<Product>?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("__v")
    val v: Int?
)