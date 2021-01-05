package com.codecool.epub.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.network.api.DataApi
import kotlinx.coroutines.flow.Flow

class Repository(private val dataApi: DataApi) {

    companion object {
        const val NETWORK_PAGE_SIZE = 1
    }

    suspend fun getStreams(cursor: String?, categoryId: String?, limit: Int): StreamsResponse = dataApi.getStreams(cursor, categoryId, limit)

    suspend fun getTopCategories(limit: Int): CategoryResponse = dataApi.getCategories(limit)

    fun getCategoryStreams(categoryId: String): Flow<PagingData<StreamsResponse.Stream>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StreamPagingSource(dataApi, categoryId) }
        ).flow
    }

   suspend fun searchCategory(query: String): CategoryResponse = dataApi.searchCategories(query)
}