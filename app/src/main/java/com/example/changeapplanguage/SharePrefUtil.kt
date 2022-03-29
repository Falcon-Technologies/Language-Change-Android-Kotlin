package com.example.changeapplanguage

import android.content.Context
import android.content.SharedPreferences

class SharePrefUtil constructor( context: Context){
    var sharedPreferences: SharedPreferences = context.getSharedPreferences("ChangeLanguageSharedPref",
        Context.MODE_PRIVATE
    )

    var appLanguage: Int
        get() = sharedPreferences.getInt("AppLanguage", -1)
        set(value) {
            sharedPreferences.edit().putInt("AppLanguage", value).apply()
        }
}