package com.codecool.epub.api

import com.codecool.epub.model.OAuth
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface TwitchApi {

    @POST("oauth2/token")
    suspend fun getAuthToken(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("grant_type") grant_type: String = "client_credentials"
    ): Response<OAuth>
}