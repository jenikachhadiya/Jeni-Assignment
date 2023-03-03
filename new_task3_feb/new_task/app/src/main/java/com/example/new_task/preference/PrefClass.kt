package com.example.new_task.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.new_task.R
import com.example.new_task.api.post.modal.ApiUser
import com.example.new_task.entity.User
import com.google.android.datatransport.cct.internal.LogResponse.fromJson
import com.google.gson.Gson


class PrefClass(var context: Context) {
    var SharedPref = context.getString(R.string.sheredpref)
    var IsLogin = context.getString(R.string.is_login)
    var splash = context.getString(R.string.SplashScreen)
    var sharedEmail = context.getString(R.string.email)
    var sharedPass = context.getString(R.string.password)
    var addToBag = context.getString(R.string.addtobag)


    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SharedPref, Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()

    fun setLoginStatus(status: Boolean) {
        editor.putBoolean(IsLogin, status)
        editor.commit()

    }

    fun getLoginStatus(): Boolean {
        return sharedPreferences.getBoolean(IsLogin, false)
    }

    fun logout() {
        editor.remove(AppConstans.USER_DATA).commit()
    }

    fun insertRecode(email: String, pass: String) {
        editor.putString(sharedEmail, email)
        editor.putString(sharedPass, pass).commit()
    }

    fun saveUserData(user: User) {
        val json = Gson().toJson(user)
        editor.putString(AppConstans.USER_DATA, json).commit()
    }

    fun saveUserDataApi(user: ApiUser) {
        val json = Gson().toJson(user)
        editor.putString(AppConstans.USER_DATA, json).commit()
    }
    fun getUserDataApi():ApiUser? {
        val json: String? = sharedPreferences.getString(AppConstans.USER_DATA, null)
        return Gson().fromJson(json, ApiUser::class.java)
    }


    fun getUserData():User? {
        val json: String? = sharedPreferences.getString(AppConstans.USER_DATA, null)
        return Gson().fromJson(json, User::class.java)
    }

    fun removeData() {
        editor.remove(sharedEmail)
        editor.remove(sharedPass)
            .commit()

    }

    fun addToBagData(addBag: Boolean) {
        editor.putBoolean(addToBag, addBag)
        editor.commit()
    }


    fun getBagStatus(): Boolean {
        return sharedPreferences.getBoolean(addToBag, false)
    }

    fun setSplashStatus(status: Boolean) {
        editor.putBoolean(splash, status)
        editor.commit()

    }

    fun getSplash(): Boolean {
        return sharedPreferences.getBoolean(splash, false)
    }


    fun firebaseTo(status: Boolean) {
        editor.putBoolean("status", status)
        editor.commit()
    }

    fun fromFirebase(): Boolean {
        return sharedPreferences.getBoolean("Firebase", false)
    }


}