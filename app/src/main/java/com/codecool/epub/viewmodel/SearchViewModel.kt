package com.codecool.epub.viewmodel

import androidx.lifecycle.ViewModel
import com.codecool.epub.data.Repository
import com.codecool.epub.model.CategoryResponse

class SearchViewModel(private val repository: Repository) : ViewModel() {

    suspend fun searchCategory(query: String): CategoryResponse = repository.searchCategory(query)

}