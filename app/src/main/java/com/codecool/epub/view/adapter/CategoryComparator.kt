package com.codecool.epub.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.codecool.epub.model.CategoryResponse

object CategoryComparator : DiffUtil.ItemCallback<CategoryResponse.Category>() {
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