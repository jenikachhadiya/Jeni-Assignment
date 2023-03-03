package com.example.app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var image:String?= null,
    var name:String? = null,
    var email:String? = null,
    var password:String? = null,
    var number:String ? = null,
    var date:String? = null,
    var city:String? = null
)
