package com.codecool.epub.model

import java.lang.Exception

sealed class RecommendationData{

    data class OnSuccess(val topStreamsResponse: StreamsResponse,
                         val topCategories: CategoryResponse,
                         val recommendedStreams1: StreamsResponse,
                         val recommendedStreams2: StreamsResponse) : RecommendationData()

    data class OnError(val exception: Exception) : RecommendationData()
}