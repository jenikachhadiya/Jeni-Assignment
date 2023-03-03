package com.example.new_task.api.post.`interface`

import android.content.Context
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.new_task.preference.AppConstans
import com.example.new_task.preference.PrefClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClint {


    companion object {

        private var retrofit: Retrofit? = null
        //val okHttpClient = OkHttpClient.Builder()

        fun init(context: Context): ApiService {
             val client = OkHttpClient.Builder().addInterceptor{ chain ->
                 val newRequest = chain.request().newBuilder()
                     .addHeader(AppConstans.HEADER_KEY,"${PrefClass(context).getUserDataApi()?.userApi}")
                     .build()
                 chain.proceed(newRequest)
             }.build()
            Log.e(TAG, "init: ${PrefClass(context).getUserData()?.token}")


            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit!!.create(ApiService::class.java)

        }


    }

}