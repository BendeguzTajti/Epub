package com.codecool.epub.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryDetailsHeaderBinding
import com.codecool.epub.model.CategoryResponse

class CategoryStreamHeaderViewHolder(
    private val binding: CategoryDetailsHeaderBinding,
    private val requestManager: RequestManager,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup, requestManager: RequestManager): CategoryStreamHeaderViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_details_header, parent, false)
            val binding = CategoryDetailsHeaderBinding.bind(view)
            return CategoryStreamHeaderViewHolder(binding, requestManager)
        }
    }

    fun bind(category: CategoryResponse.Category) {
        val boxArtWidthPx = itemView.resources.getDimensionPixelSize(R.dimen.details_box_art_width)
        val boxArtHeightPx = itemView.resources.getDimensionPixelSize(R.dimen.details_box_art_height)
        requestManager.load(category.getImageUrl(boxArtWidthPx, boxArtHeightPx))
            .thumbnail(0.5f)
            .into(binding.categoryImage)
        binding.categoryName.text = category.name
    }

}