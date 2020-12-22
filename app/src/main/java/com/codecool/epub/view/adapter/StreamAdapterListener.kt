package com.codecool.epub.view.adapter

import com.codecool.epub.model.StreamsResponse

interface StreamAdapterListener {
    fun onStreamClicked(stream: StreamsResponse.Stream)
}