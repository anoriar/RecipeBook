package com.example.recipebook.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.databinding.CategoryFilterItemBinding
import com.example.recipebook.domain.entity.Category
import com.example.recipebook.presentation.adapter_callback.CategoryDiffCallback

class CategoryFilterAdapter: ListAdapter<Category, CategoryFilterAdapter.CategoryViewHolder>(
    CategoryDiffCallback()
) {

    class CategoryViewHolder(val categoryFilterItemBinding: CategoryFilterItemBinding) : RecyclerView.ViewHolder(categoryFilterItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryFilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Category = getItem(position)
        holder.categoryFilterItemBinding.category = category
    }


}