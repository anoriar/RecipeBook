package com.example.recipebook.presentation.adapter_callback

import androidx.recyclerview.widget.DiffUtil
import com.example.recipebook.domain.entity.Category

class CategoryDiffCallback:
    DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}