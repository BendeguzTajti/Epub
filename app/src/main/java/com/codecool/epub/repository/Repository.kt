package com.codecool.epub.repository

import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.network.api.DataApi
import retrofit2.Response

class Repository(private val dataApi: DataApi) {

    suspend fun getTopStreams(limit: Int): StreamsResponse = dataApi.getTopStreams(limit)

    suspend fun getTopStreamsByCategory(categoryId: String, limit: Int): StreamsResponse = dataApi.getTopStreamsByCategory(categoryId, limit)

    suspend fun getTopCategories(): CategoryResponse = dataApi.getTopCategories()

    suspend fun getStreamsResponseByCategory(categoryId: String): Response<StreamsResponse> = dataApi.getStreamsResponseByCategory(categoryId)
}