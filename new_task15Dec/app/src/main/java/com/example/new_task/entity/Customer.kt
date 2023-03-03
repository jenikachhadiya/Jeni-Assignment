package com.example.new_task.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Customer(
    //var username: String? = null,
    //var  email: String? = null
    var message: String? = null
) : Parcelable
