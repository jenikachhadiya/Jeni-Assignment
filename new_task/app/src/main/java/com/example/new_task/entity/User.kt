package com.example.new_task.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
 @PrimaryKey(autoGenerate = true)
 var id:Int=0,
 var name:String,
 var email:String,
 var pass :String,
 var img :String?

)