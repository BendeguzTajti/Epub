package com.codecool.epub.view.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.StreamsResponse
import com.codecool.epub.view.viewholder.CategoryStreamHeaderViewHolder
import com.codecool.epub.view.viewholder.CategoryStreamHolder

class CategoryStreamAdapter(private val requestManager: RequestManager,
                            private val category: CategoryResponse.Category) :
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
        return if (viewType == R.layout.category_stream_item) {
            CategoryStreamHolder.create(parent, requestManager)
        } else {
            CategoryStreamHeaderViewHolder.create(parent, requestManager)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == R.layout.category_stream_item) {
            val offset = 1
            val currentStream = getItem(position - offset)
            currentStream?.let {
                (holder as CategoryStreamHolder).bind(it)
            }
        } else {
            (holder as CategoryStreamHeaderViewHolder).bind(category)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> R.layout.category_details_header
            else -> R.layout.category_stream_item
        }
    }

}