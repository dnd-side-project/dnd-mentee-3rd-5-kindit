package com.dnd.kindit.util

import android.content.Context
import android.content.SharedPreferences


class PreferenceManager {
    companion object {
        private const val PREFERENCES_NAME = "KINDIT_PREFERENCE"
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_BOOLEAN = false
        private const val DEFAULT_VALUE_INT = -1
        private const val DEFAULT_VALUE_LONG = -1L
        private const val DEFAULT_VALUE_FLOAT = -1f

        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        }

        fun setString(context: Context, key: String?, value: String?) {
            val prefs = getPreferences(context)
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun setBoolean(context: Context, key: String?, value: Boolean) {
            val prefs = getPreferences(context)
            val editor = prefs.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun setInt(context: Context, key: String?, value: Int) {
            val prefs = getPreferences(context)
            val editor = prefs.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun setLong(context: Context, key: String?, value: Long) {
            val prefs = getPreferences(context)
            val editor = prefs.edit()
            editor.putLong(key, value)
            editor.apply()
        }

        fun setFloat(context: Context, key: String?, value: Float) {
            val prefs = getPreferences(context)
            val editor = prefs.edit()
            editor.putFloat(key, value)
            editor.apply()
        }

        fun getString(context: Context, key: String?): String? {
            val prefs = getPreferences(context)
            return prefs.getString(key, DEFAULT_VALUE_STRING)
        }

        fun getBoolean(context: Context, key: String?): Boolean {
            val prefs = getPreferences(context)
            return prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN)
        }

        fun getInt(context: Context, key: String?): Int {
            val prefs = getPreferences(context)
            return prefs.getInt(key, DEFAULT_VALUE_INT)
        }

        fun getLong(context: Context, key: String?): Long {
            val prefs = getPreferences(context)
            return prefs.getLong(key, DEFAULT_VALUE_LONG)
        }

        fun getFloat(context: Context, key: String?): Float {
            val prefs = getPreferences(context)
            return prefs.getFloat(key, DEFAULT_VALUE_FLOAT)
        }

        fun removeKey(context: Context, key: String?) {
            val prefs = getPreferences(context)
            val edit = prefs.edit()
            edit.remove(key)
            edit.apply()
        }

        fun clear(context: Context) {
            val prefs = getPreferences(context)
            val edit = prefs.edit()
            edit.clear()
            edit.apply()
        }
    }
}