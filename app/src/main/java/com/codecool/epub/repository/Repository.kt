package com.codecool.epub.repository

import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.network.api.DataApi

class Repository(private val dataApi: DataApi) {

    suspend fun getTopStreams(limit: Int): StreamsResponse = dataApi.getStreams(null, limit)

    suspend fun getTopStreamsByCategory(categoryId: String, limit: Int): StreamsResponse = dataApi.getStreams(categoryId, limit)

    suspend fun getTopCategories(limit: Int): CategoryResponse = dataApi.getCategories(limit)
}