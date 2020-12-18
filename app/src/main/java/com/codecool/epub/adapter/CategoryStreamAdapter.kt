package com.codecool.epub.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryDetailsHeaderBinding
import com.codecool.epub.databinding.CategoryStreamItemBinding
import com.codecool.epub.model.GamesResponse
import com.codecool.epub.model.StreamsResponse

class CategoryStreamAdapter(private val requestManager: RequestManager,
                            private val game: GamesResponse.Game) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_STREAM = 2
    }

    private var streams: List<StreamsResponse.Stream> = emptyList()

    inner class CategoryHeaderHolder(private val headerBinding: CategoryDetailsHeaderBinding) : RecyclerView.ViewHolder(headerBinding.root) {

        fun bind() {
            val boxArtWidthPx = itemView.resources.getDimensionPixelSize(R.dimen.details_box_art_width)
            val boxArtHeightPx = itemView.resources.getDimensionPixelSize(R.dimen.details_box_art_height)
            requestManager.load(game.getImageUrl(boxArtWidthPx, boxArtHeightPx))
                .thumbnail(0.05f)
                .into(headerBinding.categoryImage)
            headerBinding.categoryName.text = game.name
        }
    }

    inner class CategoryStreamHolder(private val itemBinding: CategoryStreamItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(currentStream: StreamsResponse.Stream) {
            val resources = itemView.resources
            val thumbnailWidthPx = getThumbnailWidthPx(resources)
            val thumbnailHeightPx = getThumbnailHeightPx(resources)
            requestManager.load(currentStream.getThumbnailUrl(thumbnailWidthPx, thumbnailHeightPx))
                .thumbnail(0.05f)
                .into(itemBinding.recommendedStreamThumbnail)
            itemBinding.recommendedStreamTitle.text = currentStream.title
            itemBinding.recommendedStreamerName.text = currentStream.userName
            itemBinding.recommendedStreamCategoryName.text = currentStream.gameName
            if (currentStream.isLive()) {
                itemBinding.recommendedStreamViewerCount.text = getViewerCountText(currentStream, resources)
                itemBinding.recommendedStreamLiveTag.visibility = View.VISIBLE
                itemBinding.recommendedStreamViewerCount.visibility = View.VISIBLE
            }
        }

        private fun getThumbnailWidthPx(resources: Resources): Int {
            val displayMetrics = resources.displayMetrics
            val screenWidthPx = displayMetrics.widthPixels
            val recyclerViewPaddingPx = resources.getDimensionPixelSize(R.dimen.recycler_view_padding_side) * 2
            val cardMarginPx = resources.getDimensionPixelSize(R.dimen.card_margin) * 2
            return screenWidthPx - recyclerViewPaddingPx - cardMarginPx
        }

        private fun getThumbnailHeightPx(resources: Resources): Int = resources.getDimensionPixelSize(R.dimen.category_stream_thumbnail_height)

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