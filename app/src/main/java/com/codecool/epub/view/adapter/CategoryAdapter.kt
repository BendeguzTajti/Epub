package com.codecool.epub.view.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryItemBinding
import com.codecool.epub.model.CategoryResponse

class CategoryAdapter(
    private val categoryLoader: RequestBuilder<Drawable>,
    private val onCategoryClicked: (CategoryResponse.Category) -> Unit
) : ListAdapter<CategoryResponse.Category, CategoryAdapter.CategoryHolder>(CategoryComparator),
    ListPreloader.PreloadModelProvider<CategoryResponse.Category> {

    inner class CategoryHolder(
        private val binding: CategoryItemBinding,
        private val onCategoryClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { onCategoryClicked(bindingAdapterPosition) }
        }

        fun bind(category: CategoryResponse.Category) {
            categoryLoader.load(category.getImageUrl(categoryLoader.overrideWidth, categoryLoader.overrideHeight))
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

    override fun getPreloadItems(position: Int): MutableList<CategoryResponse.Category> = mutableListOf(getItem(position))

    override fun getPreloadRequestBuilder(category: CategoryResponse.Category): RequestBuilder<Drawable> {
        return categoryLoader.load(category.getImageUrl(categoryLoader.overrideWidth, categoryLoader.overrideHeight))
    }
}