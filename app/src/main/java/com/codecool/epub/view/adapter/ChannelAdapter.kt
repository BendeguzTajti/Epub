package com.codecool.epub.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codecool.epub.R
import com.codecool.epub.model.ChannelResponse

class ChannelAdapter(private val channels: List<ChannelResponse.Channel>) : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ChannelAdapter.ViewHolder, position: Int) {
        holder.bindItems(channels[position])
    }

    override fun getItemCount(): Int {
        return channels.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(channel: ChannelResponse.Channel) {
            val textViewTitle = itemView.findViewById<TextView>(R.id.category_title)
            textViewTitle.text = channel.title
            val thumbnailImage = itemView.findViewById<ImageView>(R.id.boxArt)
            Glide.with(itemView.context)
                    .load(channel.getImageUrl(130, 180))
                    .override(130, 180)
                    .into(thumbnailImage)
        }
    }
}