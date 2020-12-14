package com.codecool.epub.network.api

import com.codecool.epub.model.GamesResponse
import com.codecool.epub.model.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface DataApi {

    @GET("helix/games/top")
    suspend fun getTopGames(): Response<GamesResponse>


    @GET("helix/videos?")
    suspend fun getVideosByGameId(@Query("game_id") game_id: String): Response<VideoResponse>
}