package com.example.new_task.api.post.`interface`

import android.content.Context
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG

import com.example.new_task.preference.AppConstans
import com.example.new_task.preference.PrefClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClint {


    companion object {

         var retrofit: Retrofit? = null


        fun init(context: Context): ApiService {
         //   val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

            val logging = HttpLoggingInterceptor()

                logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder().addInterceptor{ chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(AppConstans.HEADER_KEY, "${PrefClass(context).getUserDataApi()?.userApi}")
                    .build()
                chain.proceed(newRequest)
            }.addInterceptor(logging).build()
            Log.e(TAG, "init: ${PrefClass(context).getUserData()?.token}")


            retrofit = Retrofit.Builder()
                .baseUrl(AppConstans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit!!.create(ApiService::class.java)

        }

        fun proClint(context: Context): ApiService {
            retrofit = Retrofit.Builder()
                .baseUrl(AppConstans.GET_BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit!!.create(ApiService::class.java)

        }







    }

}