package com.codecool.epub.view.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.codecool.epub.view.viewholder.CategoryStreamsLoadStateHolder

class CategoryStreamLoadStateAdapter : LoadStateAdapter<CategoryStreamsLoadStateHolder>() {

    override fun onBindViewHolder(holder: CategoryStreamsLoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CategoryStreamsLoadStateHolder {
        return CategoryStreamsLoadStateHolder.create(parent)
    }
}