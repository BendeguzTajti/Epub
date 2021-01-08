package com.codecool.epub.view.adapter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.codecool.epub.R
import com.codecool.epub.databinding.RecommendedStreamItemBinding
import com.codecool.epub.model.StreamsResponse

class RecommendedStreamAdapter(
    private val thumbnailLoader: RequestBuilder<Drawable>,
    private val onStreamClicked: (StreamsResponse.Stream) -> Unit
) : ListAdapter<StreamsResponse.Stream, RecommendedStreamAdapter.RecommendedStreamHolder>(SteamComparator),
    ListPreloader.PreloadModelProvider<StreamsResponse.Stream> {

    inner class RecommendedStreamHolder(
        private val binding: RecommendedStreamItemBinding,
        private val onStreamClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { onStreamClicked(bindingAdapterPosition) }
        }

        fun bind(stream: StreamsResponse.Stream) {
            val resources = itemView.resources
            thumbnailLoader.load(stream.getThumbnailUrl(thumbnailLoader.overrideWidth, thumbnailLoader.overrideHeight))
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
                "${stream.getViewerCountRounded()}${resources.getString(R.string.thousand_short)} ${resources.getString(
                    R.string.viewers)}"
            } else {
                "${stream.getViewerCountRounded()} ${resources.getString(R.string.viewers)}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedStreamHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_stream_item, parent, false)
        val binding = RecommendedStreamItemBinding.bind(view)
        return RecommendedStreamHolder(binding) {
            onStreamClicked(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: RecommendedStreamHolder, position: Int) {
        val stream = getItem(position)
        holder.bind(stream)
    }

    override fun getPreloadItems(position: Int): MutableList<StreamsResponse.Stream> = mutableListOf(getItem(position))

    override fun getPreloadRequestBuilder(stream: StreamsResponse.Stream): RequestBuilder<Drawable> {
        return thumbnailLoader.clone()
            .load(stream.getThumbnailUrl(thumbnailLoader.overrideWidth, thumbnailLoader.overrideHeight))
    }
}