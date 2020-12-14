package com.codecool.epub.repository

import com.codecool.epub.model.GamesResponse
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.network.api.DataApi
import retrofit2.Response

class Repository(private val dataApi: DataApi) {

    suspend fun getTopGames(): Response<GamesResponse> = dataApi.getTopGames()

    suspend fun getStreamsByGameId(gameId: String): Response<StreamsResponse> = dataApi.getStreamsByGameId(gameId)
}