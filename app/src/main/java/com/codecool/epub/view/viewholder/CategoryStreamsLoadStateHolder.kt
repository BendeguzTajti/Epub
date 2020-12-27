package com.codecool.epub.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryLoadStateFooterItemBinding

class CategoryStreamsLoadStateHolder(
    private val binding: CategoryLoadStateFooterItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): CategoryStreamsLoadStateHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_load_state_footer_item, parent, false)
            val binding = CategoryLoadStateFooterItemBinding.bind(view)
            return CategoryStreamsLoadStateHolder(binding)
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            val errorMessage = loadState.error.localizedMessage
        }
        binding.categoryStreamsLoading.isVisible = loadState is LoadState.Loading
    }
}