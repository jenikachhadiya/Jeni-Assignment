package com.example.new_task.api.post.modal

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("name") var name: String? = null,
    @SerializedName("dob")  var dob: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password")  var password: String? = null,
    @SerializedName("mobile_no") var mobile_no: String? = null,
    @SerializedName("msg") var msg :String? = null
)
