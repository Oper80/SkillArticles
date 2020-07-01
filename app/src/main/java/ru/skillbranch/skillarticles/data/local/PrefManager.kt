package ru.skillbranch.skillarticles.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PrefManager(context: Context) : PreferenceManager(context) {
    val preferences : SharedPreferences by lazy { getDefaultSharedPreferences(context) }

    fun clearAll(){
        val ed = preferences.edit()
        ed.clear()
        ed.apply()
    }
}