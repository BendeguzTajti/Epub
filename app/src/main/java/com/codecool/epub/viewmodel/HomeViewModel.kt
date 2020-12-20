package com.codecool.epub.viewmodel

import androidx.lifecycle.*
import com.codecool.epub.model.Recommendation
import com.codecool.epub.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _recommendationData = MutableLiveData<Recommendation>()
    val recommendationData: LiveData<Recommendation> = _recommendationData

    fun getRecommendations() {
        if (_recommendationData.value == null) {
            viewModelScope.launch {
                try {
                    coroutineScope {
                        val limit = 8
                        val topStreamsDeferred = async { repository.getTopStreams(limit) }
                        val topCategoriesDeferred = async { repository.getTopCategories() }
                        val topCategoriesResponse = topCategoriesDeferred.await()
                        val topStreamsResponse = topStreamsDeferred.await()

                        val recommendedCategory1 = topCategoriesResponse.data.random()
                        val recommendedCategory2 = topCategoriesResponse.data
                            .filterNot { it.id == recommendedCategory1.id }
                            .random()

                        val recommendedStreamsDeferred1 = async { repository.getTopStreamsByCategory(recommendedCategory1.id, limit) }
                        val recommendedStreamsDeferred2 = async { repository.getTopStreamsByCategory(recommendedCategory2.id, limit) }
                        val recommendedStreamsResponse1 = recommendedStreamsDeferred1.await()
                        val recommendedStreamsResponse2 = recommendedStreamsDeferred2.await()
                        _recommendationData.value = Recommendation.OnSuccess(
                            topStreamsResponse,
                            topCategoriesResponse,
                            recommendedStreamsResponse1,
                            recommendedStreamsResponse2
                        )
                    }
                } catch (exception: Exception) {
                    _recommendationData.value = Recommendation.OnError(exception)
                }
            }
        }
    }
}