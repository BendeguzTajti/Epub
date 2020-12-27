package com.codecool.epub.view.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.view.viewholder.CategoryStreamHolder

class CategoryStreamAdapter(private val requestManager: RequestManager) :
    PagingDataAdapter<StreamsResponse.Stream, RecyclerView.ViewHolder>(STREAM_COMPARATOR) {

    companion object {
        private val STREAM_COMPARATOR = object : DiffUtil.ItemCallback<StreamsResponse.Stream>() {
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryStreamHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentStream = getItem(position)
        currentStream?.let {
            (holder as CategoryStreamHolder).bind(it, requestManager)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) R.layout.category_stream_item else R.layout.category_load_state_footer_item
    }
}