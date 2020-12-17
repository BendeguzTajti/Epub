package com.codecool.epub.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.databinding.RecommendedStreamItemBinding
import com.codecool.epub.model.StreamsResponse

class RecommendedStreamAdapter(private val requestManager: RequestManager) : RecyclerView.Adapter<RecommendedStreamAdapter.RecommendedStreamHolder>() {

    private var streams: List<StreamsResponse.Stream> = emptyList()

    inner class RecommendedStreamHolder(private val itemBinding: RecommendedStreamItemBinding,
                                        private val resources: Resources) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(currentStream: StreamsResponse.Stream) {
            val thumbnailWidthPx = resources.getDimensionPixelSize(R.dimen.recommended_stream_thumbnail_width)
            val thumbnailHeightPx = resources.getDimensionPixelSize(R.dimen.recommended_stream_thumbnail_height)
            requestManager.load(currentStream.getThumbnailUrl(thumbnailWidthPx, thumbnailHeightPx))
                .thumbnail(0.05f)
                .into(itemBinding.recommendedStreamThumbnail)
            itemBinding.recommendedStreamTitle.text = currentStream.title
            itemBinding.recommendedStreamerName.text = currentStream.userName
            itemBinding.recommendedStreamCategoryName.text = currentStream.gameName
            if (currentStream.isLive()) {
                val viewerCount = if(currentStream.isViewerCountHigh()) {
                    "${currentStream.getViewerCountRounded()}${resources.getString(R.string.thousand_short)} ${resources.getString(R.string.viewers)}"
                } else {
                    "${currentStream.getViewerCountRounded()} ${resources.getString(R.string.viewers)}"
                }
                itemBinding.recommendedStreamViewerCount.text = viewerCount
                itemBinding.recommendedStreamLiveTag.visibility = View.VISIBLE
                itemBinding.recommendedStreamViewerCount.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedStreamHolder {
        val itemBinding = RecommendedStreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedStreamHolder(itemBinding, parent.resources)
    }

    override fun onBindViewHolder(holder: RecommendedStreamHolder, position: Int) {
        val currentStream = streams[position]
        holder.bind(currentStream)
    }

    override fun getItemCount(): Int = streams.size

    fun submitList(attachments: List<StreamsResponse.Stream>) {
        streams = attachments
        notifyDataSetChanged()
    }
}