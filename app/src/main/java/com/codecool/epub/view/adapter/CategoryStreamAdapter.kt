package com.codecool.epub.view.adapter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryStreamItemBinding
import com.codecool.epub.model.StreamsResponse

class CategoryStreamAdapter(
    private val thumbnailLoader: RequestBuilder<Drawable>,
    private val onStreamClicked: (StreamsResponse.Stream?) -> Unit
) : PagingDataAdapter<StreamsResponse.Stream, RecyclerView.ViewHolder>(SteamComparator) {

    inner class CategoryStreamHolder(
        private val binding: CategoryStreamItemBinding,
        private val onStreamClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { onStreamClicked(bindingAdapterPosition) }
        }

        fun bind(stream: StreamsResponse.Stream) {
            val resources = itemView.resources
            thumbnailLoader.load(stream.getThumbnailUrl(thumbnailLoader.overrideWidth, thumbnailLoader.overrideHeight))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.categoryStreamThumbnail)
            binding.categoryStreamTitle.text = stream.title
            binding.categoryStreamerName.text = stream.userName
            binding.categoryStreamCategoryName.text = stream.categoryName
            if (stream.isLive()) {
                binding.categoryStreamViewerCount.text =
                    getViewerCountText(stream, resources)
                binding.categoryStreamLiveTag.visibility = View.VISIBLE
                binding.categoryStreamViewerCount.visibility = View.VISIBLE
            }
        }

        private fun getViewerCountText(
            currentStream: StreamsResponse.Stream,
            resources: Resources
        ): String {
            return if (currentStream.isViewerCountHigh()) {
                "${currentStream.getViewerCountRounded()}${resources.getString(R.string.thousand_short)} ${resources.getString(R.string.viewers)}"
            } else {
                "${currentStream.getViewerCountRounded()} ${resources.getString(R.string.viewers)}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_stream_item, parent, false)
        val binding = CategoryStreamItemBinding.bind(view)
        return CategoryStreamHolder(binding) {
            onStreamClicked(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentStream = getItem(position)
        currentStream?.let {
            (holder as CategoryStreamHolder).bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) R.layout.category_stream_item else R.layout.category_load_state_footer_item
    }
}