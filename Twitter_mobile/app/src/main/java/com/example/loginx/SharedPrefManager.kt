package com.example.loginx

import android.content.Context

class SharedPrefManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUserSession(name: String, email: String, dob: String) {
        prefs.edit().apply {
            putString("name", name)
            putString("email", email)
            putString("dob", dob)
            putBoolean("isLoggedIn", true)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun getName(): String? = prefs.getString("name", null)
    fun getEmail(): String? = prefs.getString("email", null)
    fun getDob(): String? = prefs.getString("dob", null)
}
