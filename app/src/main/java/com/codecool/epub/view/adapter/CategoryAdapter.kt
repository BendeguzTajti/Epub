package com.codecool.epub.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryItemBinding
import com.codecool.epub.model.CategoryResponse

class CategoryAdapter(private val requestManager: RequestManager,
                      private var listener: CategoryAdapterListener) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private var categories: List<CategoryResponse.Category> = emptyList()

    inner class CategoryHolder(private val itemBinding: CategoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(currentGame: CategoryResponse.Category) {
            val resources = itemView.resources
            val boxArtWidthPx = resources.getDimensionPixelSize(R.dimen.box_art_width)
            val boxArtHeightPx = resources.getDimensionPixelSize(R.dimen.box_art_height)
            requestManager.load(currentGame.getImageUrl(boxArtWidthPx, boxArtHeightPx))
                .thumbnail(0.5f)
                .into(itemBinding.boxArt)
        }

        override fun onClick(v: View?) {
            val category = categories[adapterPosition]
            listener.onCategoryClicked(category)
        }

        init {
            itemBinding.root.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val currentGame = categories[position]
        holder.bind(currentGame)
    }

    override fun getItemCount(): Int = categories.size

    fun submitList(attachments: List<CategoryResponse.Category>) {
        categories = attachments
        notifyDataSetChanged()
    }

    interface CategoryAdapterListener {
        fun onCategoryClicked(category: CategoryResponse.Category)
    }
}