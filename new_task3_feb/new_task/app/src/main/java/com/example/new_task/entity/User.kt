package com.example.new_task.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.auth.api.signin.GoogleSignInAccount


@Entity
data class User(
 @PrimaryKey(autoGenerate = true)
 var id:Int=0,
 var name:String? = null,
 var email:String? = null,
 var pass:String? = null,
 var img:String? = null,
 var dob:String? = null,
 var googleSignIn: String? = null,
 var faceBook:String? = null,
 var phoneNumber:String? = null,
 var type:String? = null,
 var token:String? = null

)