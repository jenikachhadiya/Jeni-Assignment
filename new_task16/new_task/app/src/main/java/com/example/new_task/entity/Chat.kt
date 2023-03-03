package com.example.new_task.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class Chat(
    var id:Int,
    var img:String,
    var name:String,
    var detail:String
): Parcelable