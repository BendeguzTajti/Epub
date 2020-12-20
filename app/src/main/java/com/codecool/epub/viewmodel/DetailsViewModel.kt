package com.codecool.epub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _categoryStreamsData = MutableLiveData<Response<StreamsResponse>>()
    val categoryStreamsData: LiveData<Response<StreamsResponse>> = _categoryStreamsData

    fun getStreams(categoryId: String) {
        if (_categoryStreamsData.value == null) {
            viewModelScope.launch {
                val categoryStreamsResponse = repository.getStreamsResponseByCategory(categoryId)
                _categoryStreamsData.value = categoryStreamsResponse
            }
        }
    }
}