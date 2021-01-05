package com.codecool.epub.network.api

import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.ChannelResponse
import com.codecool.epub.model.StreamsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface DataApi {

    @GET("helix/streams")
    suspend fun getStreams(@Query("after") cursor: String?,
                           @Query("game_id") categoryId: String?,
                           @Query("first") limit: Int): StreamsResponse


    @GET("helix/games/top")
    suspend fun getCategories(@Query("first") limit: Int): CategoryResponse

    @GET("helix/search/categories")
    suspend fun searchCategories(@Query("query")searchQuery: String): CategoryResponse

    @GET("helix/search/channels")
    suspend fun searchChannels(@Query("query")searchQuery: String): ChannelResponse
}