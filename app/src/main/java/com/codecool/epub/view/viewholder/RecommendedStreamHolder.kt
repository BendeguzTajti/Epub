package com.codecool.epub.view.viewholder

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codecool.epub.R
import com.codecool.epub.databinding.RecommendedStreamItemBinding
import com.codecool.epub.model.StreamsResponse

class RecommendedStreamHolder(
    private val binding: RecommendedStreamItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): RecommendedStreamHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recommended_stream_item, parent, false)
            val binding = RecommendedStreamItemBinding.bind(view)
            return RecommendedStreamHolder(binding)
        }
    }

    fun bind(stream: StreamsResponse.Stream, requestManager: RequestManager) {
        val resources = itemView.resources
        val thumbnailWidthPx = resources.getDimensionPixelSize(R.dimen.recommended_stream_thumbnail_width)
        val thumbnailHeightPx = resources.getDimensionPixelSize(R.dimen.recommended_stream_thumbnail_height)
        requestManager.load(stream.getThumbnailUrl(thumbnailWidthPx, thumbnailHeightPx))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .thumbnail(0.5f)
            .into(binding.recommendedStreamThumbnail)
        binding.recommendedStreamTitle.text = stream.title
        binding.recommendedStreamerName.text = stream.userName
        binding.recommendedStreamCategoryName.text = stream.categoryName
        if (stream.isLive()) {
            binding.recommendedStreamViewerCount.text = getViewerCountText(stream, resources)
            binding.recommendedStreamLiveTag.visibility = View.VISIBLE
            binding.recommendedStreamViewerCount.visibility = View.VISIBLE
        }
    }

    private fun getViewerCountText(stream: StreamsResponse.Stream, resources: Resources): String {
        return if(stream.isViewerCountHigh()) {
            "${stream.getViewerCountRounded()}${resources.getString(R.string.thousand_short)} ${resources.getString(R.string.viewers)}"
        } else {
            "${stream.getViewerCountRounded()} ${resources.getString(R.string.viewers)}"
        }
    }
}