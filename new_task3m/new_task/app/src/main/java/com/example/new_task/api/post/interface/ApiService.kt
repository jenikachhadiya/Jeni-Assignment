package com.example.new_task.api.post.`interface`


import com.example.new_task.api.Get.modal.getUserResponse
import com.example.new_task.api.Get.modal.proGet
import com.example.new_task.api.Get.modal.shopListGet
import com.example.new_task.api.Get.modal.shopPurches
import com.example.new_task.api.post.modal.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    //register
    @Multipart
    @POST("user/signup")
    fun fromField(
        @Part ("name")name: RequestBody,
        @Part ("dob")dob: RequestBody?,
        @Part ("email")email: RequestBody,
        @Part ("password")password: RequestBody,
        @Part ("mobile_no") mobile_no: RequestBody,
        @Part ("upload")upload: RequestBody?,
        ): Call<ResponseData>

    //login
    @POST("user/login")
    fun loginFrom(@Body loginUser: loginUser): Call<ResponseData>


    //Get Data
    @GET("profile/users")
    fun getUserData(): Call<getUserResponse>

    //change Password
    @POST("profile/change_password")
    fun changePassword(): Call<changePass>

    //Get ProfileData
    @GET("profile/info")
    fun getProfileData(): Call<ResponseData>

    //Put UpdateProfile
    @PUT("profile/update")
    fun updateProfile(): Call<ResponseData>

    //AutoSlider Img
    @GET("products?limit=5")
    fun autoSlider():Call<MutableList<AutoSliderGet>>

    //shopList
    @GET("products")
    fun shopListData(@Query("limit")limit:Int):Call<ArrayList<shopListGet?>>

    //shopPur
    @GET("products/1")
    fun shopPurData():Call<shopPurches>

    //jew Row
    @GET("products/category/jewelery")
    fun jeweleryData():Call<MutableList<shopListGet>>

    @GET("carts")
    fun cartGet():Call<MutableList<proGet>>

    //Cart Item
    @POST("carts")
    fun cartsData(@Body carts: Carts):Call<Carts>


}