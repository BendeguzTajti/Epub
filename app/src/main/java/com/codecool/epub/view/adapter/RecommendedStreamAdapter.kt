package com.codecool.epub.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.view.viewholder.RecommendedStreamHolder

class RecommendedStreamAdapter(private val requestManager: RequestManager) :
    ListAdapter<StreamsResponse.Stream, RecyclerView.ViewHolder>(SteamComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecommendedStreamHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val stream = getItem(position)
        (holder as RecommendedStreamHolder).bind(stream, requestManager)
    }
}