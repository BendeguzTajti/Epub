package com.codecool.epub.network

import android.content.SharedPreferences
import com.codecool.epub.model.OAuth
import com.codecool.epub.util.Constants.Companion.TOKEN_KEY
import com.google.gson.Gson
import java.util.*

class TokenManager(private val sharedPreferences: SharedPreferences) {

    private var authToken: OAuth?

    init {
        val json = sharedPreferences.getString(TOKEN_KEY, null)
        authToken = Gson().fromJson(json, OAuth::class.java)
    }

    fun hasAuthToken(): Boolean {
        return authToken != null
    }

    fun isTokenExpired(): Boolean {
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        calendar.timeInMillis = authToken?.getExpirationTimeInMillis() ?: 0
        val expirationDate = calendar.time
        return currentDate.after(expirationDate)
    }

    fun saveAuthToken(authToken: OAuth?) {
        val calendar = Calendar.getInstance()
        authToken?.tokenSavedTime = calendar.timeInMillis
        this.authToken = authToken
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(authToken)
        editor.putString(TOKEN_KEY, json)
        editor.apply()
    }

    fun getAuthToken(): OAuth? {
        return authToken
    }
}