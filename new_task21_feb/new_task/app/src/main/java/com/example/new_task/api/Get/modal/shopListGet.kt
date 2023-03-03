package com.example.new_task.api.Get.modal

import com.google.gson.annotations.SerializedName


data class shopListGet(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("rating")
    val rating: Rating? = null,
    @SerializedName("title")
    val title: String? = null,
    var counter :Int
) {

    data class Rating(
        @SerializedName("count")
        val count: Int? = null,
        @SerializedName("rate")
        val rate: Double? = null
    )
}