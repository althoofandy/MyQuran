package com.example.myquran.ui.pray.pref

import android.content.Context
import android.content.SharedPreferences

class PrayPref(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("PrayPref", Context.MODE_PRIVATE)

    fun saveDataId(id: String) {
        val editor = sharedPreferences.edit()
        editor.putString(ID_KEY, id)
        editor.apply()
    }

    fun getDataId(): String? {
        return sharedPreferences.getString(ID_KEY, null)
    }

    companion object {
        private const val ID_KEY = "id"
    }
}