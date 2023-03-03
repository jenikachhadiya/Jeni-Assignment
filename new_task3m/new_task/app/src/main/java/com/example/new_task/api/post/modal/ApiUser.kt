package com.example.new_task.api.post.modal

import com.google.gson.annotations.SerializedName


data class ApiUser(

    @SerializedName("user_id") var userid: String? = null,
    @SerializedName("user_name") var userName: String? = null,
    @SerializedName("user_dob") var userDob: String? = null,
    @SerializedName("user_profilepic") var userProfilePic: String? = null,
    @SerializedName("user_email") var userEmail: String? = null,
    @SerializedName("user_password") var userPassword: String? = null,
    @SerializedName("user_mobile_no") var userMobileNo: String? = null,
    @SerializedName("user_api") var userApi: String? = null,
    @SerializedName("user_is_verify") var userIsVerify: String? = null,
    @SerializedName("user_update_datetime") var userUpdateDateTime: String? = null,
    @SerializedName("user_created_datetime") var userCreatedDateTime: String? = null

)
