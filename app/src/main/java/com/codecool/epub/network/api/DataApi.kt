package com.codecool.epub.network.api

import com.codecool.epub.model.GamesResponse
import retrofit2.Response
import retrofit2.http.GET

interface DataApi {

    @GET("helix/games/top")
    suspend fun getTopGames(): Response<GamesResponse>
}