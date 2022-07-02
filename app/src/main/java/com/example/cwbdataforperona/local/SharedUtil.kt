package com.example.cwbdataforperona.local

import android.content.Context
import android.content.SharedPreferences
import com.example.cwbdataforperona.R

object SharedUtil {

    fun saveFirstOpenValue(context: Context, key: String, value: Boolean) {
        val sharePre: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor = sharePre.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getFirstOpenValue(context: Context, key: String?): Boolean {
        val sharePre: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        return sharePre.getBoolean(key, true)
    }

}