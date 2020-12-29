package com.codecool.epub.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.codecool.epub.model.CategoryResponse
import com.codecool.epub.view.viewholder.CategoryHolder

class CategoryAdapter(private val requestManager: RequestManager) :
    ListAdapter<CategoryResponse.Category, RecyclerView.ViewHolder>(CATEGORY_COMPARATOR) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = getItem(position)
        (holder as CategoryHolder).bind(category, requestManager)
    }
}