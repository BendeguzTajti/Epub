package com.codecool.epub.view.adapter

import android.content.res.Configuration
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryStreamItemBinding
import com.codecool.epub.model.StreamsResponse

class CategoryStreamAdapter(private val onStreamClicked: (StreamsResponse.Stream?) -> Unit) :
    PagingDataAdapter<StreamsResponse.Stream, RecyclerView.ViewHolder>(SteamComparator) {

    inner class CategoryStreamHolder(
        private val binding: CategoryStreamItemBinding,
        private val onStreamClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { onStreamClicked(bindingAdapterPosition) }
        }

        fun bind(stream: StreamsResponse.Stream) {
            val resources = itemView.resources
            val thumbnailWidthPx = getThumbnailWidthPx(resources)
            val thumbnailHeightPx = getThumbnailHeightPx(resources)
            Glide.with(itemView.context)
                .load(stream.getThumbnailUrl(thumbnailWidthPx, thumbnailHeightPx))
                .override(thumbnailWidthPx, thumbnailHeightPx)
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

        private fun getThumbnailWidthPx(resources: Resources): Int {
            val displayMetrics = resources.displayMetrics
            val screenWidthPx = displayMetrics.widthPixels
            val recyclerViewPaddingPx = resources.getDimensionPixelSize(R.dimen.recycler_view_padding_side)
            val cardMarginPx = resources.getDimensionPixelSize(R.dimen.card_margin)
            return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                val columnCount = 2
                screenWidthPx / columnCount - recyclerViewPaddingPx * 2 - cardMarginPx
            } else {
                screenWidthPx - recyclerViewPaddingPx * 2 - cardMarginPx * 2
            }
        }

        private fun getThumbnailHeightPx(resources: Resources): Int {
            return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                resources.getDimensionPixelSize(R.dimen.category_stream_thumbnail_height_landscape)
            } else {
                resources.getDimensionPixelSize(R.dimen.category_stream_thumbnail_height_portrait)
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