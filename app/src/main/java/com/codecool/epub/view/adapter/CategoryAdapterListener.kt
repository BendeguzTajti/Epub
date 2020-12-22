package com.codecool.epub.view.adapter

import com.codecool.epub.model.CategoryResponse

interface CategoryAdapterListener {
    fun onCategoryClicked(category: CategoryResponse.Category)
}