package com.codecool.epub.view.adapter

import android.content.res.Configuration
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryDetailsHeaderBinding
import com.codecool.epub.databinding.CategoryStreamItemBinding
import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.model.StreamsResponse

class CategoryStreamAdapter(private val requestManager: RequestManager,
                            private val category: CategoryResponse.Category) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_STREAM = 2
    }

    private var streams: List<StreamsResponse.Stream> = emptyList()

    inner class CategoryHeaderHolder(private val headerBinding: CategoryDetailsHeaderBinding) : RecyclerView.ViewHolder(headerBinding.root) {

        fun bind() {
            val boxArtWidthPx = itemView.resources.getDimensionPixelSize(R.dimen.details_box_art_width)
            val boxArtHeightPx = itemView.resources.getDimensionPixelSize(R.dimen.details_box_art_height)
            requestManager.load(category.getImageUrl(boxArtWidthPx, boxArtHeightPx))
                .thumbnail(0.05f)
                .into(headerBinding.categoryImage)
            headerBinding.categoryName.text = category.name
        }
    }

    inner class CategoryStreamHolder(private val itemBinding: CategoryStreamItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(currentStream: StreamsResponse.Stream) {
            val resources = itemView.resources
            val thumbnailWidthPx = getThumbnailWidthPx(resources)
            val thumbnailHeightPx = getThumbnailHeightPx(resources)
            requestManager.load(currentStream.getThumbnailUrl(thumbnailWidthPx, thumbnailHeightPx))
                .thumbnail(0.05f)
                .into(itemBinding.categoryStreamThumbnail)
            itemBinding.categoryStreamTitle.text = currentStream.title
            itemBinding.categoryStreamerName.text = currentStream.userName
            itemBinding.categoryStreamCategoryName.text = currentStream.categoryName
            if (currentStream.isLive()) {
                itemBinding.categoryStreamViewerCount.text = getViewerCountText(currentStream, resources)
                itemBinding.categoryStreamLiveTag.visibility = View.VISIBLE
                itemBinding.categoryStreamViewerCount.visibility = View.VISIBLE
            }
        }

        private fun getThumbnailWidthPx(resources: Resources): Int {
            val displayMetrics = resources.displayMetrics
            val screenWidthPx = displayMetrics.widthPixels
            return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                val recyclerViewPaddingPx = resources.getDimensionPixelSize(R.dimen.recycler_view_padding_side)
                val cardMarginPx = resources.getDimensionPixelSize(R.dimen.card_margin)
                val columnCount = 2
                screenWidthPx / columnCount - recyclerViewPaddingPx * 2 - cardMarginPx
            } else {
                val recyclerViewPaddingPx = resources.getDimensionPixelSize(R.dimen.recycler_view_padding_side)
                val cardMarginPx = resources.getDimensionPixelSize(R.dimen.card_margin)
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

        private fun getViewerCountText(currentStream: StreamsResponse.Stream, resources: Resources): String {
            return if(currentStream.isViewerCountHigh()) {
                "${currentStream.getViewerCountRounded()}${resources.getString(R.string.thousand_short)} ${resources.getString(R.string.viewers)}"
            } else {
                "${currentStream.getViewerCountRounded()} ${resources.getString(R.string.viewers)}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_HEADER -> {
                val headerBinding = CategoryDetailsHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CategoryHeaderHolder(headerBinding)
            }
            else -> {
                val itemBinding = CategoryStreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CategoryStreamHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_HEADER) {
            val viewHolder = holder as CategoryHeaderHolder
            viewHolder.bind()
        } else if (holder.itemViewType == TYPE_STREAM) {
            val viewHolder = holder as CategoryStreamHolder
            val currentStream = streams[position-1]
            viewHolder.bind(currentStream)
        }
    }

    override fun getItemCount(): Int = streams.size + 1

    override fun getItemViewType(position: Int): Int = if (position == 0) TYPE_HEADER else TYPE_STREAM

    fun submitList(attachments: List<StreamsResponse.Stream>) {
        streams = attachments
        notifyDataSetChanged()
    }
}