package com.codecool.epub.view.adapter

import android.widget.ImageView
import com.codecool.epub.model.StreamsResponse

interface StreamAdapterListener {
    fun onStreamClicked(stream: StreamsResponse.Stream, imageView: ImageView)
}