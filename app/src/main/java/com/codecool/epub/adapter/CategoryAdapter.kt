package com.codecool.epub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.model.GameResponse

class CategoryAdapter(private val requestManager: RequestManager,
                      private val games : List<GameResponse.Game>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boxArt: ImageView = itemView.findViewById(R.id.boxArt)
        val name: TextView = itemView.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(itemView)
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGame = games[position]
        requestManager.load(currentGame.getImageUrl())
            .into(holder.boxArt)
        holder.name.text = currentGame.name
    }

    override fun getItemCount(): Int {
        return games.size
    }
}