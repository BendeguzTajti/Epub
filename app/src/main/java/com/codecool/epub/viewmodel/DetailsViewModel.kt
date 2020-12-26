package com.codecool.epub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codecool.epub.data.Repository
import com.codecool.epub.model.StreamsResponse
import kotlinx.coroutines.flow.Flow

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private var currentStreamResults: Flow<PagingData<StreamsResponse.Stream>>? = null

    fun getCategoryStreams(categoryId: String): Flow<PagingData<StreamsResponse.Stream>> {
        val lastResult = currentStreamResults
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<StreamsResponse.Stream>> = repository.getCategoryStreams(categoryId)
            .cachedIn(viewModelScope)
        currentStreamResults = newResult
        return newResult
    }
}