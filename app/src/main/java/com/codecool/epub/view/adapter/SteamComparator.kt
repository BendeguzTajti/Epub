package com.codecool.epub.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.codecool.epub.model.StreamsResponse

object SteamComparator : DiffUtil.ItemCallback<StreamsResponse.Stream>() {
    override fun areItemsTheSame(
        oldItem: StreamsResponse.Stream,
        newItem: StreamsResponse.Stream
    ): Boolean =
        oldItem.userId == newItem.userId

    override fun areContentsTheSame(
        oldItem: StreamsResponse.Stream,
        newItem: StreamsResponse.Stream
    ): Boolean =
        oldItem == newItem
}