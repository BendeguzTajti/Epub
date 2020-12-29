package com.codecool.epub.viewmodel

import androidx.lifecycle.*
import com.codecool.epub.model.RecommendationData
import com.codecool.epub.data.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val isRefreshing = MutableLiveData<Boolean>()
    private val recommendationData: MutableLiveData<RecommendationData> by lazy {
        MutableLiveData<RecommendationData>().also {
            getRecommendations()
        }
    }

    fun isRefreshing(): LiveData<Boolean> = isRefreshing

    fun recommendationData(): LiveData<RecommendationData> = recommendationData

    fun refreshData() {
        isRefreshing.value = true
        getRecommendations()
    }

    private fun getRecommendations() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val limit = 8
                    val topStreamsDeferred = async { repository.getStreams(null, null, limit) }
                    val topCategoriesDeferred = async { repository.getTopCategories(limit) }
                    val topCategoriesResponse = topCategoriesDeferred.await()
                    val topStreamsResponse = topStreamsDeferred.await()

                    val recommendedCategory1 = topCategoriesResponse.data.random()
                    val recommendedCategory2 = topCategoriesResponse.data
                        .filterNot { it.id == recommendedCategory1.id }
                        .random()

                    val recommendedStreamsDeferred1 = async { repository.getStreams(null, recommendedCategory1.id, limit) }
                    val recommendedStreamsDeferred2 = async { repository.getStreams(null, recommendedCategory2.id, limit) }
                    val recommendedStreamsResponse1 = recommendedStreamsDeferred1.await()
                    val recommendedStreamsResponse2 = recommendedStreamsDeferred2.await()
                    recommendationData.value = RecommendationData.OnSuccess(
                        topStreamsResponse.data,
                        topCategoriesResponse.data,
                        recommendedStreamsResponse1.data,
                        recommendedStreamsResponse2.data
                    )
                    isRefreshing.value = false
                }
            } catch (exception: Exception) {
                recommendationData.value = RecommendationData.OnError(exception)
                isRefreshing.value = false
            }
        }
    }
}