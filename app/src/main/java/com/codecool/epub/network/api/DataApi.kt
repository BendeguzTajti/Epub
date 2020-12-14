package com.codecool.epub.network.api

import com.codecool.epub.model.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    @GET("helix/games/top")
    suspend fun getTopGames(@Query("first") limit: Int = 8): Response<GamesResponse>
}