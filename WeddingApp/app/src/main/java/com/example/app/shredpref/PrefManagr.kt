package com.example.app.shredpref

import android.content.Context
import android.content.SharedPreferences

class PrefManagr (var context: Context){


    val PEREF_NAME = "my-pref"
    val KEY_STATUS =  "isLogin"


    var sharedPreferences:SharedPreferences =  context.getSharedPreferences(PEREF_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun  setLoginStatus(status:Boolean){
        editor.putBoolean(KEY_STATUS,status)
        editor.commit()
    }

    fun getLoginStatus():Boolean{
        return sharedPreferences.getBoolean(KEY_STATUS,false)
    }
}