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
        println("Hello + ${currentStream.thumbnailUrl}")
        requestManager.load(currentStream.getThumbnailUrl(220, 120)).into(holder.thumbnail)
        holder.streamTitle.text = currentStream.title
        holder.streamerName.text = currentStream.userName
        holder.categoryName.text = currentStream.gameName
    }

    override fun getItemCount(): Int = streams.size

    fun submitList(attachments: List<StreamsResponse.Stream>) {
        streams = attachments
        notifyDataSetChanged()
    }
}