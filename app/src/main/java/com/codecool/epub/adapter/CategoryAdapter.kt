package com.codecool.epub.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.model.GamesResponse

class CategoryAdapter(private val requestManager: RequestManager,
                      private var listener: CategoryAdapterListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var games: List<GamesResponse.Game> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val resources: Resources = itemView.context.resources
        val boxArt: ImageView = itemView.findViewById(R.id.boxArt)

        override fun onClick(v: View?) {
            val game = games[adapterPosition]
            listener.onCategoryClicked(game)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(itemView)
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGame = games[position]
        val boxArtWidthPx = holder.resources.getDimensionPixelSize(R.dimen.box_art_width)
        val boxArtHeightPx = holder.resources.getDimensionPixelSize(R.dimen.box_art_height)
        requestManager.load(currentGame.getImageUrl(boxArtWidthPx, boxArtHeightPx)).into(holder.boxArt)
    }

    override fun getItemCount(): Int = games.size

    fun submitList(attachments: List<GamesResponse.Game>) {
        games = attachments
        notifyDataSetChanged()
    }

    interface CategoryAdapterListener {
        fun onCategoryClicked(game: GamesResponse.Game)
    }
}