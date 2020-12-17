package com.codecool.epub.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryItemBinding
import com.codecool.epub.model.GamesResponse

class CategoryAdapter(private val requestManager: RequestManager,
                      private var listener: CategoryAdapterListener) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private var games: List<GamesResponse.Game> = emptyList()

    inner class CategoryHolder(private val itemBinding: CategoryItemBinding,
                               private val resources: Resources) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(currentGame: GamesResponse.Game) {
            val boxArtWidthPx = resources.getDimensionPixelSize(R.dimen.box_art_width)
            val boxArtHeightPx = resources.getDimensionPixelSize(R.dimen.box_art_height)
            requestManager.load(currentGame.getImageUrl(boxArtWidthPx, boxArtHeightPx))
                .thumbnail(0.05f)
                .into(itemBinding.boxArt)
        }

        override fun onClick(v: View?) {
            val game = games[adapterPosition]
            listener.onCategoryClicked(game)
        }

        init {
            itemBinding.root.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(itemBinding, parent.resources)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val currentGame = games[position]
        holder.bind(currentGame)
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