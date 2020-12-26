package com.codecool.epub.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.codecool.epub.R
import com.codecool.epub.databinding.CategoryLoadStateFooterItemBinding
import com.google.android.material.snackbar.Snackbar

class CategoryStreamsLoadStateViewHolder(
    private val binding: CategoryLoadStateFooterItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CategoryStreamsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_load_state_footer_item, parent, false)
            val binding = CategoryLoadStateFooterItemBinding.bind(view)
            return CategoryStreamsLoadStateViewHolder(binding, retry)
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            val context = itemView.context
            val errorMessage = loadState.error.localizedMessage
            errorMessage?.let {
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
                    .setAction(context.getString(R.string.retry)) {
                        retry.invoke()
                }.show()
            }
        }
        binding.categoryStreamsLoading.isVisible = loadState is LoadState.Loading
    }

}