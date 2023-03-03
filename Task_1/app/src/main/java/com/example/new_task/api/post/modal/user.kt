package com.example.new_task.api.post.modal

import android.net.Uri
import com.bumptech.glide.load.resource.SimpleResource
import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class user(
    var name: String,
    var dob: String?,
    var email: String,
    var password: String,
    var mobile_no: String,

)
