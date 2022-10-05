package com.thenotesgiver.onlinenotessaver.utils

import android.content.Context
import com.thenotesgiver.onlinenotessaver.utils.Constant.constaPref
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor (@ApplicationContext context: Context) {

    private val preps = context.getSharedPreferences(constaPref,Context.MODE_PRIVATE)

    fun saveToken(string: String){
        val editor = preps.edit()
        editor.putString("token",string)
        editor.apply()
    }

    fun getToken() :String?{

        return preps.getString("token",null)
    }

}