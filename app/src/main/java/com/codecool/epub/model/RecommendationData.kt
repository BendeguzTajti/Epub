package com.codecool.epub.model

import java.lang.Exception

sealed class RecommendationData{

    data class OnSuccess(val topStreams: List<StreamsResponse.Stream>,
                         val topCategories: List<CategoryResponse.Category>,
                         val recommendedStreams1: List<StreamsResponse.Stream>,
                         val recommendedStreams2: List<StreamsResponse.Stream>) : RecommendationData()

    data class OnError(val exception: Exception) : RecommendationData()
}