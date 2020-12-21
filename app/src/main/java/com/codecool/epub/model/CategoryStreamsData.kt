package com.codecool.epub.model

import java.lang.Exception

sealed class CategoryStreamsData {

    data class OnSuccess(val streamsResponse: StreamsResponse) : CategoryStreamsData()

    data class OnError(val exception: Exception) : CategoryStreamsData()
}