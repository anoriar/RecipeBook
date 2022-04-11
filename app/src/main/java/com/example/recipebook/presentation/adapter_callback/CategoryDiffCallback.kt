package com.example.recipebook.presentation.adapter_callback

import androidx.recyclerview.widget.DiffUtil
import com.example.recipebook.domain.entity.CategoryFilter

class CategoryDiffCallback:
    DiffUtil.ItemCallback<CategoryFilter>() {
    override fun areItemsTheSame(oldItem: CategoryFilter, newItem: CategoryFilter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryFilter, newItem: CategoryFilter): Boolean {
        return oldItem == newItem
    }
}