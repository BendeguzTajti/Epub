package com.codecool.epub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.model.GameResponse

class CategoryAdapter(private val requestManager: RequestManager,
                      private var listener: CategoryAdapterListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var games : List<GameResponse.Game> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val boxArt: ImageView = itemView.findViewById(R.id.boxArt)
        val name: TextView = itemView.findViewById(R.id.categoryName)
        val cardView: CardView = itemView.findViewById(R.id.categoryCard)

        override fun onClick(v: View?) {
            val game = games[adapterPosition]
            listener.onCategoryClicked(cardView, game)
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
        requestManager.load(currentGame.getImageUrl(150,200)).into(holder.boxArt)
        holder.name.text = currentGame.name
        holder.cardView.transitionName = currentGame.id
    }

    override fun getItemCount(): Int = games.size

    fun submitList(attachments: List<GameResponse.Game>) {
        games = attachments
        notifyDataSetChanged()
    }

    interface CategoryAdapterListener {
        fun onCategoryClicked(card: CardView, game: GameResponse.Game)
    }
}