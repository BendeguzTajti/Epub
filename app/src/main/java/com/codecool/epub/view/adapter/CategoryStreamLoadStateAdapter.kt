package com.codecool.epub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryLoadStateFooterItemBinding

class CategoryStreamLoadStateAdapter : LoadStateAdapter<CategoryStreamLoadStateAdapter.CategoryStreamLoadStateHolder>() {

    inner class CategoryStreamLoadStateHolder(
        private val binding: CategoryLoadStateFooterItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                val errorMessage = loadState.error.localizedMessage
            }
//            binding.categoryStreamsLoading.isVisible = loadState is LoadState.Loading
            binding.categoryStreamsLoading.isVisible = true
        }
    }

    override fun onBindViewHolder(holder: CategoryStreamLoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CategoryStreamLoadStateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_load_state_footer_item, parent, false)
        val binding = CategoryLoadStateFooterItemBinding.bind(view)
        return CategoryStreamLoadStateHolder(binding)
    }
}