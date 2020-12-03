package com.codecool.epub.api

import com.codecool.epub.model.OAuth
import com.codecool.epub.util.Constants.Companion.GRANT_TYPE
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface TwitchAuthApi {

    @POST
    fun getAuthToken(
        @Url url: String,
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("grant_type") grant_type: String = GRANT_TYPE
    ): Call<OAuth>
}