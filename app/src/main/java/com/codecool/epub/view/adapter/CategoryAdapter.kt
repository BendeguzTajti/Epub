package com.codecool.epub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryItemBinding
import com.codecool.epub.model.CategoryResponse

class CategoryAdapter(private val onCategoryClicked: (CategoryResponse.Category) -> Unit) :
    ListAdapter<CategoryResponse.Category, CategoryAdapter.CategoryHolder>(CATEGORY_COMPARATOR) {

    companion object {
        private val CATEGORY_COMPARATOR = object : DiffUtil.ItemCallback<CategoryResponse.Category>() {
            override fun areItemsTheSame(
                oldItem: CategoryResponse.Category,
                newItem: CategoryResponse.Category
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CategoryResponse.Category,
                newItem: CategoryResponse.Category
            ): Boolean =
                oldItem == newItem
        }
    }

    class CategoryHolder(
        private val binding: CategoryItemBinding,
        private val onCategoryClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { onCategoryClicked(bindingAdapterPosition) }
        }

        fun bind(category: CategoryResponse.Category) {
            val resources = itemView.resources
            val boxArtWidthPx = resources.getDimensionPixelSize(R.dimen.box_art_width)
            val boxArtHeightPx = resources.getDimensionPixelSize(R.dimen.box_art_height)
            Glide.with(itemView.context)
                .load(category.getImageUrl(boxArtWidthPx, boxArtHeightPx))
                .override(boxArtWidthPx, boxArtHeightPx)
                .thumbnail(0.5f)
                .into(binding.boxArt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        val binding = CategoryItemBinding.bind(view)
        return CategoryHolder(binding) {
            onCategoryClicked(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}