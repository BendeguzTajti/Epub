package com.codecool.epub.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codecool.epub.api.TwitchApi
import com.codecool.epub.model.OAuth
import com.codecool.epub.util.Constants.Companion.ACCESS_TOKEN_KEY
import com.codecool.epub.util.Constants.Companion.CLIENT_ID
import com.codecool.epub.util.Constants.Companion.CLIENT_SECRET
import com.codecool.epub.util.Constants.Companion.TOKEN_EXPIRATION_KEY
import retrofit2.Response

class Repository(private val sharedPreferences: SharedPreferences,
                 private val twitchApi: TwitchApi) {

//    private val authTokenData: MutableLiveData<OAuth?> = MutableLiveData()
//
//    fun requestAuthToken(): LiveData<OAuth?> {
//        val accessToken: String? = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
//        val expirationTime: Long = sharedPreferences.getLong(TOKEN_EXPIRATION_KEY, 0)
//        if (accessToken != null) authTokenData.value = OAuth(accessToken, expirationTime) else authTokenData.value = null
//        return authTokenData
//    }
//
//    suspend fun requestNewAuthToken() : Response<OAuth> {
//        return twitchApi.getAuthToken(CLIENT_ID, CLIENT_SECRET)
//    }
//
//    fun saveAuthToken(accessToken: String, expirationTime: Long) {
//        val authToken = OAuth(accessToken, expirationTime)
//        authTokenData.value = authToken
//    }
}