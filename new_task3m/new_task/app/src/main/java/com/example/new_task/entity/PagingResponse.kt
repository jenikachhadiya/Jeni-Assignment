package com.example.new_task.entity

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.datatransport.runtime.backends.BackendResponse

class PagingResponse<T, U>():PagingSource<Int,User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        TODO("Not yet implemented")
    }
}