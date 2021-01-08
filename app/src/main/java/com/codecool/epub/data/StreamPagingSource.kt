package com.codecool.epub.data

import androidx.paging.PagingSource
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.network.api.DataApi
import java.lang.Exception

class StreamPagingSource(
    private val service: DataApi,
    private val categoryId: String
) : PagingSource<String, StreamsResponse.Stream>() {

    companion object {
        private const val LIMIT = 4
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, StreamsResponse.Stream> {
        val cursor = params.key
        return try {
            val response = service.getStreams(cursor, categoryId, LIMIT)
            val streams = response.data
            LoadResult.Page(
                data = streams,
                prevKey = cursor,
                nextKey = response.pagination.cursor
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}