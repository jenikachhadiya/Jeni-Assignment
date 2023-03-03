package com.example.new_task.Prefrencee

import android.content.Context
import android.content.SharedPreferences
import com.example.new_task.R


class PrefClass(var context: Context) {
    var SharedPref = context.getString(R.string.sheredpref)
    var IsLogin = context.getString(R.string.is_login)
    var sharedEmail = context.getString(R.string.email)
    var sharedPass = context.getString(R.string.password)
    var addToBag = context.getString(R.string.addtobag)


    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(SharedPref, Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()

    fun setLoginStatus(status: Boolean){
        editor.putBoolean(IsLogin,status)
        editor.commit()

    }
    fun getLoginStatus():Boolean{
        return  sharedPreferences.getBoolean(IsLogin,false)
    }
    fun logout(){
        editor.clear()
        editor.commit()
    }
    fun insertRecode(email:String,pass:String){
        editor.putString(sharedEmail,email)
        editor.putString(sharedPass,pass)
        editor.commit()
    }
    fun removeData(){
        editor.remove(sharedEmail)
        editor.remove(sharedPass)
            .commit()

    }

    fun addToBagData(addBag:Boolean){
        editor.putBoolean(addToBag,addBag)
        editor.commit()
    }

    fun getBagStatus():Boolean{
        return sharedPreferences.getBoolean(addToBag,false)
    }














}