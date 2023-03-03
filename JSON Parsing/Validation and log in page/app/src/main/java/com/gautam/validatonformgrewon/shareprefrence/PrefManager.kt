package com.gautam.validatonformgrewon.shareprefrence

import android.content.Context
import android.content.SharedPreferences
import com.gautam.validatonformgrewon.apimodal.RegisterResponse
import com.gautam.validatonformgrewon.modal.RememberMe
import com.gautam.validatonformgrewon.modal.Users
import com.google.gson.Gson

class PrefManager(context: Context) {

    val PEREF_NAME = "my-pref"
    val KEY_LOGIN = "login"
    val KEY_INTRO = "isIntro"
    val KEY_REMEBER_ME = "remember_me"
    val Status = "goole_facebook"
    val KET_API="api to login"
    var TOKEN="api token"

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PEREF_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveLoginCredentials(user: Users) {
        val credentialToJson = Gson().toJson(user)
        return sharedPreferences.edit().putString(KEY_LOGIN, credentialToJson).apply()
    }

    fun getLoginCredentials(): Users? {
        return Gson().fromJson(sharedPreferences.getString(KEY_LOGIN, ""), Users::class.java)
    }

    fun saveApiData(user:RegisterResponse){
        val apiloginTOJson=Gson().toJson(user)
       return sharedPreferences.edit().putString(KET_API,apiloginTOJson).apply()
    }

    fun getApi():RegisterResponse?{
        return  Gson().fromJson(sharedPreferences.getString(KET_API,""),RegisterResponse::class.java)
    }
    fun saveToken(token:String){
        editor.putString(TOKEN,token).apply()
    }

    fun getToken():String?{
        return sharedPreferences.getString(TOKEN,"")
    }



    fun saveRememberMe(user: RememberMe) {
        editor.putString(KEY_REMEBER_ME, Gson().toJson(user)).apply()
    }

    fun getRememberMe(): RememberMe? {
        return Gson().fromJson(
            sharedPreferences.getString(KEY_REMEBER_ME, ""), RememberMe::class.java
        )
    }

    fun clear() {
        editor.remove(KEY_LOGIN).apply()
        editor.remove(Status).apply()
        editor.remove(KET_API).apply()
    }

}