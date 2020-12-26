package com.codecool.epub.view.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.codecool.epub.view.viewholder.CategoryStreamsLoadStateViewHolder

class CategoryStreamLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<CategoryStreamsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: CategoryStreamsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CategoryStreamsLoadStateViewHolder {
        return CategoryStreamsLoadStateViewHolder.create(parent, retry)
    }
}