package com.codecool.epub.view.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.codecool.epub.view.viewholder.CategoryStreamLoadStateHolder

class CategoryStreamLoadStateAdapter : LoadStateAdapter<CategoryStreamLoadStateHolder>() {

    override fun onBindViewHolder(holder: CategoryStreamLoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CategoryStreamLoadStateHolder {
        return CategoryStreamLoadStateHolder.create(parent)
    }
}