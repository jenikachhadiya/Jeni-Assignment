package com.example.new_task.api.Get.modal

import com.example.new_task.api.post.modal.ApiUser

data class getUserResponse(
    var msg:String,
    var data:List<ApiUser>
)
