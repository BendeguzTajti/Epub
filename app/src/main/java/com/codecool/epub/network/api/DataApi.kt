package com.codecool.epub.network.api

import com.codecool.epub.model.GamesResponse
import com.codecool.epub.model.StreamsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface DataApi {

    @GET("helix/streams")
    suspend fun getTopStreams(@Query("first") limit: Int): StreamsResponse

    @GET("helix/streams")
    suspend fun getTopStreamsByCategory(@Query("game_id") categoryId: String,
                                        @Query("first") limit: Int): StreamsResponse

    @GET("helix/games/top")
    suspend fun getTopCategories(@Query("first") limit: Int = 8): GamesResponse

    @GET("helix/streams?")
    suspend fun getStreamsByGameId(@Query("game_id") game_id: String): Response<StreamsResponse>
}