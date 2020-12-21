package com.codecool.epub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecool.epub.model.CategoryStreamsData
import com.codecool.epub.repository.Repository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val categoryStreamsData: MutableLiveData<CategoryStreamsData> by lazy { MutableLiveData() }

    fun getCategoryStreamsData(): LiveData<CategoryStreamsData> = categoryStreamsData

    fun getStreams(categoryId: String) {
        if (categoryStreamsData.value == null) {
            viewModelScope.launch {
                try {
                    coroutineScope {
                        val limit = 10
                        val categoryStreamsResponse = repository.getTopStreamsByCategory(categoryId, limit)
                        categoryStreamsData.value = CategoryStreamsData.OnSuccess(categoryStreamsResponse)
                    }
                } catch (exception: Exception) {
                    categoryStreamsData.value = CategoryStreamsData.OnError(exception)
                }
            }
        }
    }
}