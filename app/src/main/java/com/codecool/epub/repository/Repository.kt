package com.codecool.epub.repository

import com.codecool.epub.model.GamesResponse
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.network.api.DataApi
import retrofit2.Response

class Repository(private val dataApi: DataApi) {

    suspend fun getTopStreams(limit: Int): StreamsResponse = dataApi.getTopStreams(limit)

    suspend fun getTopStreamsByCategory(categoryId: String, limit: Int): StreamsResponse = dataApi.getTopStreamsByCategory(categoryId, limit)

    suspend fun getTopCategories(): GamesResponse = dataApi.getTopCategories()

    suspend fun getStreamsByGameId(gameId: String): Response<StreamsResponse> = dataApi.getStreamsByGameId(gameId)
}