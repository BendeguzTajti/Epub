package com.codecool.epub.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.model.StreamsResponse

class StreamsAdapter(private val requestManager: RequestManager) : RecyclerView.Adapter<StreamsAdapter.ViewHolder>() {

    private var streams: List<StreamsResponse.Stream> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resources: Resources = itemView.context.resources
        val thumbnail: ImageView = itemView.findViewById(R.id.stream_thumbnail)
        val liveTag: TextView = itemView.findViewById(R.id.live_tag)
        val viewerCount: TextView = itemView.findViewById(R.id.viewer_count)
        val streamTitle: TextView = itemView.findViewById(R.id.stream_title)
        val streamerName: TextView = itemView.findViewById(R.id.streamer_name)
        val categoryName: TextView = itemView.findViewById(R.id.stream_category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.stream_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StreamsAdapter.ViewHolder, position: Int) {
        val currentStream = streams[position]
        requestManager.load(currentStream.getThumbnailUrl(220, 120)).into(holder.thumbnail)
        holder.streamTitle.text = currentStream.title
        holder.streamerName.text = currentStream.userName
        holder.categoryName.text = currentStream.gameName
        if (currentStream.isLive()) {
            val viewerCount = if(currentStream.isViewerCountHigh()) {
                "${currentStream.getViewerCountRounded()}${holder.resources.getString(R.string.thousand_short)} ${holder.resources.getString(R.string.viewers)}"
            } else {
                "${currentStream.getViewerCountRounded()} ${holder.resources.getString(R.string.viewers)}"
            }
            holder.viewerCount.text = viewerCount
            holder.liveTag.visibility = View.VISIBLE
            holder.viewerCount.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = streams.size

    fun submitList(attachments: List<StreamsResponse.Stream>) {
        streams = attachments
        notifyDataSetChanged()
    }
}