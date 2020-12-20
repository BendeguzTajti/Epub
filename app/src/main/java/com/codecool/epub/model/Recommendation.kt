package com.codecool.epub.model

import java.lang.Exception

sealed class Recommendation{

    data class OnSuccess(val topStreamsResponse: StreamsResponse,
                         val topCategories: CategoryResponse,
                         val recommendedStreams1: StreamsResponse,
                         val recommendedStreams2: StreamsResponse) : Recommendation()

    data class OnError(val exception: Exception) : Recommendation()
}