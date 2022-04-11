package com.example.recipebook.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.databinding.CategoryFilterItemBinding
import com.example.recipebook.domain.entity.CategoryFilter
import com.example.recipebook.presentation.adapter_callback.CategoryDiffCallback

class CategoryFilterAdapter: ListAdapter<CategoryFilter, CategoryFilterAdapter.CategoryViewHolder>(
    CategoryDiffCallback()
) {

    var onCategoryFilterClickListener: ((CategoryFilter) -> Unit)? = null

    class CategoryViewHolder(val categoryFilterItemBinding: CategoryFilterItemBinding) : RecyclerView.ViewHolder(categoryFilterItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryFilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryFilter: CategoryFilter = getItem(position)
        holder.categoryFilterItemBinding.categoryFilter = categoryFilter
        holder.categoryFilterItemBinding.root.setOnClickListener {
            onCategoryFilterClickListener?.invoke(categoryFilter)
        }
    }


}