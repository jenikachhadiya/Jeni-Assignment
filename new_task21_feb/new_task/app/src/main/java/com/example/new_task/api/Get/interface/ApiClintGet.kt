package com.example.new_task.api.Get.`interface`

import android.content.Context
import com.example.new_task.api.post.`interface`.ApiService
import com.example.new_task.preference.AppConstans
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClintGet {
    companion object {

        var retrofit: Retrofit? = null

        fun init(context: Context): ApiService {
            retrofit = Retrofit.Builder()
                .baseUrl(AppConstans.GET_BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit!!.create(ApiService::class.java)

        }

    }
}