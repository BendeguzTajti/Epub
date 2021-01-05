package com.codecool.epub.viewmodel

import androidx.lifecycle.ViewModel
import com.codecool.epub.data.Repository
import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.ChannelResponse

class SearchViewModel(private val repository: Repository) : ViewModel() {

    suspend fun searchCategory(query: String): CategoryResponse = repository.searchCategory(query)

    suspend fun searchChannels(query: String): ChannelResponse = repository.searchChannels(query)
}