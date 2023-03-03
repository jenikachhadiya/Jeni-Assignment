package com.example.new_task.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class Chat(
    var uid:String? = null,
    var name:String?=null,
    var email:String?=null,
): Parcelable