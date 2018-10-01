package br.com.semanapesada.checkpoint

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

/**
 * Created by Alfredo L. Porfirio on 08/05/17.
 * Copyright Universo Online 2018. All rights reserved.
 */
class CheckPreferences(private val mContext: Context) {

    private val sharedPrefer: SharedPreferences
        get() = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)


    fun putString(key: String, value: String) {
        val editor = sharedPrefer.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putInt(key: String, value: Int) {
        val editor = sharedPrefer.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun putLong(key: String, value: Long) {
        val editor = sharedPrefer.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPrefer.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }


    fun putStringSet(key: String, mutableSet: MutableSet<String>) {
        val editor = sharedPrefer.edit()
        editor.putStringSet(key, mutableSet)
        editor.apply()
    }

    fun getString(key: String, defValue: String = ""): String {
        val prefs = sharedPrefer
        return prefs.getString(key, defValue) ?: defValue
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        val prefs = sharedPrefer
        return prefs.getInt(key, defValue)
    }

    fun getLong(key: String, defValue: Long = 0): Long {
        val prefs = sharedPrefer
        return prefs.getLong(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        val prefs = sharedPrefer
        return prefs.getBoolean(key, defValue)
    }

    fun getStringSet(key: String, defValue: Set<String> = setOf()): Set<String> {
        val prefs = sharedPrefer
        return prefs.getStringSet(key, defValue) ?: defValue
    }

    companion object {
        const val PREFS_NAME = "checkPrefs"
    }
}