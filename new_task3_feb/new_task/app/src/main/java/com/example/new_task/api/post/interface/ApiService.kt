package com.example.new_task.api.post.`interface`


import com.example.new_task.api.Get.modal.getUserResponse
import com.example.new_task.api.post.modal.ResponseData
import com.example.new_task.api.post.modal.changePass
import com.example.new_task.api.post.modal.loginUser
import com.example.new_task.api.post.modal.user
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {


    //register
    @POST("user/signup")
    fun fromField(@Body user: user):Call<ResponseData>

    //login
    @POST("user/login")
    fun loginFrom(@Body loginUser: loginUser):Call<ResponseData>


    //Get Data
    @GET("profile/users")
    fun getUserData():Call<getUserResponse>

    //change Password
    @POST("profile/change_password")
    fun changePassword():Call<changePass>

    //Get ProfileData
    @GET("profile/info")
    fun getProfileData():Call<ResponseData>

    //Put UpdateProfile
    @PUT("profile/update")
    fun updateProfile():Call<ResponseData>



}