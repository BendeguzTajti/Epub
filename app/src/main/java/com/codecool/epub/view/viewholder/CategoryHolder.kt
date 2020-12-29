package com.codecool.epub.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryItemBinding
import com.codecool.epub.model.CategoryResponse

class CategoryHolder(
    private val binding: CategoryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): CategoryHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)
            val binding = CategoryItemBinding.bind(view)
            return CategoryHolder(binding)
        }
    }

    fun bind(category: CategoryResponse.Category, requestManager: RequestManager) {
        val resources = itemView.resources
        val boxArtWidthPx = resources.getDimensionPixelSize(R.dimen.box_art_width)
        val boxArtHeightPx = resources.getDimensionPixelSize(R.dimen.box_art_height)
        requestManager.load(category.getImageUrl(boxArtWidthPx, boxArtHeightPx))
            .thumbnail(0.5f)
            .into(binding.boxArt)
    }
}