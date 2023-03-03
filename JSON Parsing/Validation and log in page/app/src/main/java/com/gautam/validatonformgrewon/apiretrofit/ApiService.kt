package com.gautam.validatonformgrewon.apiretrofit

import com.gautam.validatonformgrewon.apimodal.*
import com.gautam.validatonformgrewon.param.ChangePassParam
import com.gautam.validatonformgrewon.param.LoginParam
import com.gautam.validatonformgrewon.param.UpdateParam
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @Multipart
    @POST("user/signup")
    fun getSingupList(
        @Part upload: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("mobile_no") mobileno: RequestBody,
        @Part("dob") dob: RequestBody
    ): Call<RegisterResponse>
    //Call<RegisterResponse> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name)
    //  open fun uploadAttachment(@Part filePart: Part?): Call<MyResponse?>?


    @POST("user/login")
    fun userLogin(@Body param: LoginParam): Call<RegisterResponse>

    @GET("profile/users")
    fun getUserList(): Call<ProfileResponse>

    @POST("profile/change_password")
    fun useChangePasswrd(@Body param: ChangePassParam): Call<RegisterResponse>

    @PUT("profile/update")
    fun userProfileChange(@Body param: UpdateParam): Call<RegisterResponse>

    @GET("products")
    fun getListView(): Call<ArrayList<HomeResponse>>

    @GET("products?limit=5")
    fun getAutoViewImage(): Call<ArrayList<AutoViewResponse>>

    @GET("products/category/jewelery")
    fun getCategories():Call<ArrayList<CategoriesResponse>>

    @GET("products/1")
    fun getCatreUp():Call<DetailsResponse>

    @GET("carts")
    fun getCarts():Call<ArrayList<IncrementDecremen>>
}