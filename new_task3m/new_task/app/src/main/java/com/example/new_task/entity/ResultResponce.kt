package com.example.new_task.entity

import android.content.res.Resources
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.engine.Resource
import com.example.new_task.api.Get.modal.responce
import com.example.new_task.api.Get.modal.shopListGet
import com.example.new_task.api.post.`interface`.ApiClint
import com.example.new_task.api.post.`interface`.ApiService
import com.example.new_task.preference.AppConstans

class ResultResponce(val apiService:ApiClint)
    :PagingSource<Int,shopListGet>(){


    /* override fun getRefreshKey(state: PagingState<Int, shopListGet>): Int? {

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, shopListGet>{
         *//*   var postion = params.key ?: AppConstans.STARTING_PAGE_INDEX

        return when(val result = apiService){

           LoadResult.Page(
               data = result.
           )
        }*//*
    }
*/
    override fun getRefreshKey(state: PagingState<Int, shopListGet>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, shopListGet> {
        TODO("Not yet implemented")
    }
}